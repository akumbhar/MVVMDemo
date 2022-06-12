package com.example.paypay.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypay.repository.MainRepository
import com.example.paypay.repository.retrofit.ConversionResponse
import com.example.paypay.repository.retrofit.Currency
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.reflect.Type

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val apiResponseMutableLiveData: MutableLiveData<List<Currency>> = MutableLiveData()
    var apiResponseLiveData: LiveData<List<Currency>> = apiResponseMutableLiveData

    val showLoadingLiveData: MutableLiveData<Unit> = MutableLiveData()
    val hideLoadingLiveData: MutableLiveData<Unit> = MutableLiveData()
    val showUIErrorLiveData: MutableLiveData<Unit> = MutableLiveData()
    val updateValuesLiveData: MutableLiveData<Double> = MutableLiveData()


    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencies = async { repository.getCurrencies() }
            val conversions = async { repository.getConversions() }
            try {
                val currenciesResponse = currencies.await()
                val conversionResponse = conversions.await()
                processData(currenciesResponse, conversionResponse)
            } catch (ex: Exception) {
                ex.printStackTrace()
                showUIErrorLiveData.postValue(Unit)
            }

        }
    }

    private fun processData(
        currenciesResponse: Response<JsonObject>,
        conversionResponse: Response<ConversionResponse>
    ) {
        val currencyMap: HashMap<String, String>? = try {
            currenciesResponse.body()?.let {
                val type: Type = object : TypeToken<HashMap<String?, String?>?>() {}.type
                Gson().fromJson(it.toString(), type)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
        val conversionMap: HashMap<String, Double>? = try {
            conversionResponse.body()?.let {
                val type: Type = object : TypeToken<HashMap<String?, Double?>?>() {}.type
                Gson().fromJson(it.rates.toString(), type)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
        getCurrencyData(currencyMap, conversionMap)

    }

    private fun getCurrencyData(
        currencyMap: HashMap<String, String>?,
        conversionMap: HashMap<String, Double>?
    ) {

        val listCurrencies = mutableListOf<Currency>()
        currencyMap?.forEach { (currency, description) ->
            conversionMap?.entries?.find { currency == it.key }?.let { filteredMap ->
                listCurrencies.add(
                    Currency(
                        currencyName = filteredMap.key,
                        description = description,
                        conversionRate = filteredMap.value
                    )
                )
            }
        }

        if (listCurrencies.isNotEmpty()) {
            repository.deleteAllCurrencies()
            repository.insertAllCurrencies(listCurrencies)
            apiResponseMutableLiveData.postValue(repository.getAllCurrencies())
        }
    }

    fun updateCurrency(selectedCurrency: String?, inputValue: Double) {
        val selectedInputCurrency = repository.getCurrencyById(selectedCurrency ?: "USD")
        val calculatedConversionFactorByUSD =
            inputValue / selectedInputCurrency.conversionRate
        updateValuesLiveData.postValue(calculatedConversionFactorByUSD)
    }
}
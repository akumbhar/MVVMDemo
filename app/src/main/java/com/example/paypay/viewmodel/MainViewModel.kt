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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.reflect.Type

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val apiResponseMutableLiveData: MutableLiveData<List<Currency>> = MutableLiveData()
    var apiResponseLiveData: LiveData<List<Currency>> = apiResponseMutableLiveData
    val showHideLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val showUIErrorLiveData: MutableLiveData<Unit> = MutableLiveData()
    val updateValuesLiveData: MutableLiveData<Double> = MutableLiveData()


    fun getCurrencies() {
        showHideLoadingLiveData.postValue(true)
        viewModelScope.launch {

            try {
                val currencies = async { repository.getCurrencies() }
                val conversions = async { repository.getConversions() }
                val currenciesResponse = currencies.await()
                val conversionResponse = conversions.await()
                processData(currenciesResponse, conversionResponse)
            } catch (ex: Exception) {
                ex.printStackTrace()
                showHideLoadingLiveData.postValue(false)
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
            showHideLoadingLiveData.postValue(false)
            apiResponseMutableLiveData.postValue(repository.getAllCurrencies())
        }
    }

    fun updateCurrency(selectedCurrency: String?, inputValue: Double) {
        val selectedInputCurrency = repository.getCurrencyById(selectedCurrency ?: "USD")
        val calculatedConversionFactorByUSD =
            inputValue / selectedInputCurrency.conversionRate
        updateValuesLiveData.postValue(calculatedConversionFactorByUSD)
    }

    fun validateFields(currency: String?, amount: String?): Pair<Boolean, Double> {
        val inputValue: Double = try {
            amount.toString().toDouble()
        } catch (e: java.lang.Exception) {
            0.0
        }
        return Pair(inputValue != 0.0 && !currency.isNullOrEmpty(), inputValue)
    }

    fun insertValueToPrefs(value:String) = repository.insertToSharedPrefs(value)


    val myEmitLiveData = MutableLiveData<Int>()

    fun doLiveDataEmit() {

        viewModelScope.launch (Dispatchers.IO){

//            myEmitLiveData.postValue(1)
//            myEmitLiveData.postValue(2)
//            myEmitLiveData.postValue(3)
//            myEmitLiveData.postValue(4)
//            myEmitLiveData.postValue(5)
            myEmitLiveData.value = 1
            myEmitLiveData.value = 2
            myEmitLiveData.value = 3
            myEmitLiveData.value = 4
            myEmitLiveData.value = 5
        }
    }

//    lateinit var countdownFlow: Flow<nt>
//    lateinit var flow: Flow<Int>



    fun doKotlinFlowEmit() = flow{
        emit(1)
        emit(2)
        emit(3)
        emit(4)
        emit(5)



    }.flowOn(Dispatchers.IO)



    sealed class Operation {
        object ShowLoading : Operation()
        object HideLoading : Operation()
        class GetDataSuccess(val dataList : List<Currency>) : Operation()
        class ApiError(val message:String) : Operation()
    }

    val sharedFlowEmit = MutableStateFlow<Operation>(Operation.ShowLoading)

    fun loadViaApi() = viewModelScope.launch {
        sharedFlowEmit.emit(Operation.ShowLoading)
        try {
            val currenciesResponse =  repository.getCurrencies()
            val currencyMap: HashMap<String, String>? = try {
                currenciesResponse.body()?.let {
                    val type: Type = object : TypeToken<HashMap<String?, String?>?>() {}.type
                    Gson().fromJson(it.toString(), type)
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                null
            }
            if (!currencyMap.isNullOrEmpty()) {
                val listCurrencies = mutableListOf<Currency>()
                currencyMap?.forEach { (currency, description) ->
                    listCurrencies.add(Currency(currencyName = currency, description = description))
                }
                sharedFlowEmit.emit(Operation.GetDataSuccess(listCurrencies))
                sharedFlowEmit.emit(Operation.HideLoading)
            }else{
                sharedFlowEmit.emit(Operation.HideLoading)
                sharedFlowEmit.emit(Operation.ApiError("Unknown error occurred"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            sharedFlowEmit.emit(Operation.HideLoading)
            sharedFlowEmit.emit(Operation.ApiError(ex.message!!))

        }

    }

   /* fun loadViaApi() = flow {
        emit(Operation.ShowLoading)
        try {
            val currenciesResponse =  repository.getCurrencies()
            val currencyMap: HashMap<String, String>? = try {
                currenciesResponse.body()?.let {
                    val type: Type = object : TypeToken<HashMap<String?, String?>?>() {}.type
                    Gson().fromJson(it.toString(), type)
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                null
            }
            if (!currencyMap.isNullOrEmpty()) {
                val listCurrencies = mutableListOf<Currency>()
                currencyMap?.forEach { (currency, description) ->
                    listCurrencies.add(Currency(currencyName = currency, description = description))
                }
                emit(Operation.GetDataSuccess(listCurrencies))
                emit(Operation.HideLoading)
            }else{
                emit(Operation.HideLoading)
                emit(Operation.ApiError("Unknown error occurred"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Operation.HideLoading)
            emit(Operation.ApiError(ex.message!!))

        }

    }*/
}
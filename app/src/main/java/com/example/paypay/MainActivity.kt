package com.example.paypay

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paypay.common.doLogE
import com.example.paypay.common.hideKeyboard
import com.example.paypay.repository.retrofit.Currency
import com.example.paypay.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()
    private val columnCount = 3
    private var currencyList = listOf<Currency>()
    lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currencyAdapter = CurrencyAdapter(currencyList)
        with(listCurrencies) {
            adapter = currencyAdapter
            layoutManager = GridLayoutManager(
                this@MainActivity,
                columnCount
            )
        }

        mViewModel.getCurrencies()
        mViewModel.apiResponseLiveData.observe(this, Observer { currencyList ->
            currencyList?.let {
                currencyAdapter.updateList(it)
                setDropDownList(it)
            }
        })
        mViewModel.updateValuesLiveData.observe(this, Observer { factor ->
            doLogE("Convresion factor : $factor")
           currencyAdapter.updateConversionFactor(factor)
        })

        btnCalculate.setOnClickListener {


            val selectedCurrency = outlinedTextField.txtCurrencyDropDown.text.toString()

            val inputValue: Double = try {
                etInputCurrency.editText?.text.toString().toDouble()
            } catch (e: Exception) {
                0.0
            }

            if (inputValue == 0.0) {
                etInputCurrency.error = "Please enter value"
            } else {
                it.hideKeyboard()
                etInputCurrency.error = null
                currencyAdapter.updateConversionFactor(inputValue)
            }

            mViewModel.updateCurrency(selectedCurrency, inputValue)

        }
    }

    private fun setDropDownList(list: List<Currency>) {
        val currencyListForDropDown = mutableListOf<String>()
        list.map { currencyListForDropDown.add(it.currencyName) }
        val adapter = ArrayAdapter(this, R.layout.dropdown_text_row, currencyListForDropDown)
        txtCurrencyDropDown.setAdapter(adapter)
    }
}

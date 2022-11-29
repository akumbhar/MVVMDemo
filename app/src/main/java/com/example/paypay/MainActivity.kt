package com.example.paypay

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paypay.common.doLogE
import com.example.paypay.common.hideKeyboard
import com.example.paypay.repository.retrofit.Currency
import com.example.paypay.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
            layoutManager = GridLayoutManager(this@MainActivity, columnCount)
        }

//        doCurrencyThing()
//        doLiveDataThing()
        doKotlinFlowThing()

    }

    private fun doCurrencyThing() {
        mViewModel.getCurrencies()
        mViewModel.apiResponseLiveData.observe(this, Observer { currencyList ->
            currencyList?.let {
                currencyAdapter.updateList(it)
                setDropDownList(it)
            }
        })
        mViewModel.updateValuesLiveData.observe(this, Observer { factor ->
            currencyAdapter.updateConversionFactor(factor)
        })

        btnCalculate.setOnClickListener {
            val selectedCurrency = outlinedTextField.txtCurrencyDropDown.text.toString()
            val inputAmount = etInputCurrency.editText?.text.toString()
            val (isValid, amount) = mViewModel.validateFields(selectedCurrency, inputAmount)
            if (isValid) {
                etInputCurrency.hideKeyboard()
                etInputCurrency.error = null
                mViewModel.updateCurrency(selectedCurrency, amount)
            } else {
                etInputCurrency.error = getString(R.string.validation_error)
            }
        }
    }

    private fun setDropDownList(list: List<Currency>) {
        val currencyListForDropDown = mutableListOf<String>()
        list.map { currencyListForDropDown.add(it.currencyName) }
        val adapter = ArrayAdapter(this, R.layout.dropdown_text_row, currencyListForDropDown)
        txtCurrencyDropDown.setAdapter(adapter)
    }

    private fun doKotlinFlowThing() {
        mViewModel.loadViaApi()
        lifecycleScope.launch {
            mViewModel.sharedFlowEmit.collect { operation ->
                when (operation) {
                    is MainViewModel.Operation.ShowLoading -> {
                        doLogE("ShowLoading")
                    }
                    is MainViewModel.Operation.HideLoading -> {
                        doLogE("HideLoading")
                    }
                    is MainViewModel.Operation.GetDataSuccess -> {
                        doLogE("GetDataSuccess - ${operation.dataList.size}")
                    }
                    is MainViewModel.Operation.ApiError -> {
                        doLogE("GetDataSuccess - ${operation.message}")
                    }
                }
            }
        }
    }

    private fun doLiveDataThing() {
        mViewModel.doLiveDataEmit()
        mViewModel.myEmitLiveData.observe(this, Observer { factor ->
            doLogE("Live Data $factor")
        })
    }


}

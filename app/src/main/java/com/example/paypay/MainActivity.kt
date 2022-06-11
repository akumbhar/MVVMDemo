package com.example.paypay

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paypay.common.doLogE
import com.example.paypay.repository.retrofit.Currency
import com.example.paypay.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()
    private val columnCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel.getFacts()
        mViewModel.apiResponseLiveData.observe(this, Observer { it ->

            doLogE("Total currencies - ${it.size}")
            var stringBuffer = StringBuffer()
            it.forEach {

                stringBuffer.append("${it.currencyName} - ${it.description} - ${it.conversionRate}\n")
            }

//                txtData.text = stringBuffer.toString()
        })

        val currencyList = listOf(
            Currency("AED", "United Arab Emirates Dirham", 3.6731),
            Currency("AFN", "Afghan Afghani", 88.999995),
            Currency("ALL", "Albanian Lek", 112.95),
            Currency("AMD", "Armenian Dram", 429.910968),
            Currency("AFN", "Afghan Afghani", 88.999995),
            Currency("ALL", "Albanian Lek", 112.95),
            Currency("AMD", "Armenian Dram", 429.910968),
            Currency("ANG", "Netherlands Antillean Guilder", 1.803037)
        )

        with(listCurrencies) {
            adapter = CurrencyAdapter(currencyList)
            layoutManager = GridLayoutManager(
                this@MainActivity,
                columnCount
            )
        }


        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(this, R.layout.dropdown_text_row, items)
        txtCurrencyDropDown.setAdapter(adapter)
    }
}

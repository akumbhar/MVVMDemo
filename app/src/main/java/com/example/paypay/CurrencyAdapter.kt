package com.example.paypay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paypay.common.roundTo
import com.example.paypay.repository.retrofit.Currency

class CurrencyAdapter(factList: List<Currency>, conversionFactor: Double = 1.0) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private val currencyList = mutableListOf<Currency>()
    private var cFactor = 1.0

    init {
        currencyList.addAll(factList)
        cFactor = conversionFactor
    }

    inner class CurrencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle: TextView = view.findViewById(R.id.newsTitle)
        private val txtDescription: TextView = view.findViewById(R.id.txtConversionRate)

        fun setValues(currency: Currency) {
            with(currency) {
                val value = cFactor * conversionRate
                txtDescription.text = "${value.roundTo(2)}"
                txtTitle.text = currencyName

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrencyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.currency_adapter_row, parent, false)
    )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.setValues(currencyList[position])

    override fun getItemCount() = currencyList.size
    fun updateList(updatedCurrencyList: List<Currency>) {
        currencyList.clear()
        currencyList.addAll(updatedCurrencyList)
        notifyDataSetChanged()
    }

    fun updateConversionFactor(conversionFactor: Double) {
        cFactor = conversionFactor
        notifyDataSetChanged()
    }
}
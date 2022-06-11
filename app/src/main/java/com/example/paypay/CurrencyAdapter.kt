package com.example.paypay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paypay.repository.retrofit.Currency

class CurrencyAdapter(private val factList: List<Currency>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {


    inner class CurrencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle: TextView = view.findViewById(R.id.newsTitle)
        private val txtDescription: TextView = view.findViewById(R.id.txtConversionRate)

        fun setValues(currency: Currency) {
            with(currency) {
//                txtDescription.text = description
                txtTitle.text = currencyName

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrencyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.currency_adapter_row, parent, false)
    )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.setValues(factList[position])

    override fun getItemCount() = factList.size
}
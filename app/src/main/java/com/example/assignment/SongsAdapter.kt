package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.common.Song
import com.example.assignment.common.toHtml
import com.example.assignment.repository.retrofit.Currency

class SongsAdapter(factList: List<Song>) :
    RecyclerView.Adapter<SongsAdapter.CurrencyViewHolder>() {

    private val currencyList = mutableListOf<Song>()

    init {
        currencyList.addAll(factList)
    }

    inner class CurrencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        private val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        private val imgSong: ImageView = view.findViewById(R.id.imgSong)

        fun setValues(currency: Song) {
            with(currency) {
                txtTitle.text = title.toHtml()
                txtDescription.text = artist.toHtml()
                Glide.with(imgSong)
                    .load(imageUrl)
                    .into(imgSong)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrencyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.currency_adapter_row, parent, false)
    )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.setValues(currencyList[position])

    override fun getItemCount() = currencyList.size
    fun updateList(updatedCurrencyList: List<Song>) {
        currencyList.clear()
        currencyList.addAll(updatedCurrencyList)
        notifyDataSetChanged()
    }


}
package com.example.assignment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.common.Song
import com.example.assignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()
    private var songsList = listOf<Song>()
    lateinit var songsAdapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songsAdapter = SongsAdapter(songsList)
        with(listCurrencies) {
            adapter = songsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mViewModel.getSongs()
        mViewModel.apiResponseLiveData.observe(this, Observer { currencyList ->
            currencyList?.let {
                songsAdapter.updateList(it)
            }
        })

    }


}

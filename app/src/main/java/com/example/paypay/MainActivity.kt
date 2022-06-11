package com.example.paypay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.paypay.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(MainViewModel::class.java)
        mViewModel.getFacts()
        mViewModel.apiResponseLiveData.observe(this, Observer {

            /*    var stringBuffer = StringBuffer()
                it.forEach {

                    stringBuffer.append("${it.title}\n")
                }

                txtData.text = stringBuffer.toString()*/
        })
    }
}

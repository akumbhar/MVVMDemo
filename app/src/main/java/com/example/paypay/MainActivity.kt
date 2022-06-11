package com.example.paypay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.paypay.viewmodel.MainViewModel
import com.example.paypay.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(MainViewModel::class.java)
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

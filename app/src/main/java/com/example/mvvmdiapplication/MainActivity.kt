package com.example.mvvmdiapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.mvvmdiapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel.getFacts()
        mViewModel.apiResponseLiveData.observe(this, Observer {

            var stringBuffer = StringBuffer()
            it.forEach {

                stringBuffer.append("${it.title}\n")
            }

            txtData.text = stringBuffer.toString()
        })
    }
}

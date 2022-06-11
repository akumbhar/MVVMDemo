package com.example.paypay.common

import android.util.Log

fun Any.doLogE(message: String) = Log.e(this::class.java.simpleName, message)
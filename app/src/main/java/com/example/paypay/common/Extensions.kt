package com.example.paypay.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber
import java.util.*

fun Any.doLogE(message: String) =Timber.e(message)

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Number.roundTo(
    numFractionDigits: Int
) = "%.${numFractionDigits}f".format(this, Locale.ENGLISH).toDouble()
package com.example.assignment.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.text.HtmlCompat
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


fun Context.readTextFromAsset(fileName : String) : String{
    return assets.open(fileName).bufferedReader().use {
        it.readText()}
}

fun String?.toHtml() = this?.let {
    HtmlCompat.fromHtml(
        this,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
}
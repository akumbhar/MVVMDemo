package com.example.paypay

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun displayAllViews() {
        /*assertDisplayed(R.id.etInputCurrency)
        assertDisplayed(R.id.btnCalculate)
        assertDisplayed(R.id.outlinedTextField)
        assertDisplayed(R.id.txtCurrencyDropDown)
        assertDisplayed(R.id.listCurrencies)*/
        onView(allOf(withId(R.id.etInputCurrency), withText("Hello!")))
    }

}
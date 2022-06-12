package com.example.paypay

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun displayAllViews() {
        assertDisplayed(R.id.etInputCurrency)
        assertDisplayed(R.id.btnCalculate)
        assertDisplayed(R.id.outlinedTextField)
        assertDisplayed(R.id.txtCurrencyDropDown)
        assertDisplayed(R.id.listCurrencies)
    }

    @Test
    fun validateScreenTitleAndDescription() {
        assertDisplayed(R.string.app_name)
        assertDisplayed(R.string.enter_amount)
        assertDisplayed(R.string.calculate)
    }

    @Test
    fun showErrorWhenPressedCalculateWithoutEnteringNumber() {
        clickOn(R.id.btnCalculate)
        assertDisplayed(R.string.validation_error)
    }


    @Test
    fun checkNoOfItemsAfterInitialAPICall() {
        BaristaSleepInteractions.sleep(5000)
        assertRecyclerViewItemCount(R.id.listCurrencies, 169);
    }

}
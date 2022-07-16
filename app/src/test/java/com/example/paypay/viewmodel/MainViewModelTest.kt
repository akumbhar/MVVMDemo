package com.example.paypay.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.paypay.repository.MainRepository
import com.example.paypay.repository.retrofit.ConversionResponse
import com.example.paypay.utils.MainCoroutineRule
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MainViewModelTest {


    lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: MainRepository

    // for live data testing
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository)
    }


    @Test
    fun `verify show loading is initiated whenever called getCurrencies`() {
        viewModel.getCurrencies()
        Assert.assertNotNull(viewModel.showHideLoadingLiveData.value)
        Assert.assertEquals(true, viewModel.showHideLoadingLiveData.value)
    }

    @Test
    fun `return false if input currency invalid amount is valid`() {
        val invalidCurrency = null
        val validEnteredAmount = "10"
        Assert.assertFalse(viewModel.validateFields(invalidCurrency, validEnteredAmount).first)
    }

    @Test
    fun `return false if valid input invalid currency amount`() {
        val invalidCurrency = "USD"
        val validEnteredAmount = null
        Assert.assertFalse(viewModel.validateFields(invalidCurrency, validEnteredAmount).first)
    }

    @Test
    fun `return false if both input invalid `() {
        val invalidCurrency = null
        val validEnteredAmount = null
        Assert.assertFalse(viewModel.validateFields(invalidCurrency, validEnteredAmount).first)
    }

    @Test
    fun `return true if both input valid `() {
        val invalidCurrency = "USD"
        val validEnteredAmount = "100"
        Assert.assertTrue(viewModel.validateFields(invalidCurrency, validEnteredAmount).first)
    }

    @Test
    fun `show error if unknown network error occurred`() = runBlockingTest {
        whenever(repository.getCurrencies()).thenThrow(RuntimeException("Unknown error occurred"))
        viewModel.getCurrencies()
        Assert.assertNotNull(viewModel.showUIErrorLiveData.value)
        Assert.assertEquals(false, viewModel.showHideLoadingLiveData.value)
    }

    @Test
    fun `show success if api response successful`() = runBlockingTest {

        val jsonCurrencyObject = Gson().toJsonTree(getDummyCurrencyMap()).asJsonObject
        val retrofitCurrencyResponse = Response.success(200, jsonCurrencyObject)
        whenever(repository.getCurrencies()).thenReturn(retrofitCurrencyResponse)

        val jsonConversionObject =
            Gson().fromJson(getDummyConversionJson(), ConversionResponse::class.java)
        val retrofitConversionResponse = Response.success(200, jsonConversionObject)
        whenever(repository.getConversions()).thenReturn(retrofitConversionResponse)

        viewModel.getCurrencies()

        verify(repository, times(1)).deleteAllCurrencies()
        verify(repository, times(1)).insertAllCurrencies(any())
        Assert.assertEquals(false, viewModel.showHideLoadingLiveData.value)
        Assert.assertNotNull(viewModel.apiResponseLiveData.value)
    }


    private fun getDummyCurrencyJson() = "{\n" +
            "  \"AED\": \"United Arab Emirates Dirham\",\n" +
            "  \"ALL\": \"Albanian Lek\",\n" +
            "  \"AMD\": \"Armenian Dram\",\n" +
            "  \"AFN\": \"Afghan Afghani\",\n" +
            "  \"ALL\": \"Albanian Lek\"\n" +
            "}"

    private fun getDummyConversionJson() =
        "{\"disclaimer\":\"Usage subject to terms: https://openexchangerates.org/terms\",\"license\":\"https://openexchangerates.org/license\",\"timestamp\":1655056818,\"base\":\"USD\",\"rates\":{\"AED\":3.6731,\"AFN\":89.399125,\"ALL\":114.229019}}"

    private fun getDummyCurrencyMap() = mapOf(
        "AED" to "United Arab Emirates Dirham",
        "ALL" to "Albanian Lek",
        "AMD" to "Armenian Dram",
        "AFN" to "Afghan Afghani",
        "ALL" to "Albanian Lek"
    )

    private fun getDummyConversionMap() = mapOf(
        "AED" to 88.999995,
        "ALL" to 112.95,
        "AMD" to 429.910968,
        "AFN" to 88.999995,
        "ALL" to 112.95
    )
}
package com.example.paypay.viewmodel

import com.example.paypay.repository.MainRepository
import com.example.paypay.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.lang.RuntimeException

class ViewModelTestbyMokk {

    @MockK
    lateinit var repository: MainRepository

    lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `verify kotlin flow test if error`() = runBlockingTest {
        val message = "Unknown error occurred"
//        coEvery  { repository.getCurrencies() } returns RuntimeException( message)
        coEvery  { repository.getCurrencies() } returns Response.error(403,  ResponseBody.create(
            MediaType.parse("application/json"),
            "{\"key\":[\"somestuff\"]}"
        ))
        val returnedFlow = viewModel.loadViaApi().toList()

        Assert.assertEquals(MainViewModel.Operation.ShowLoading, returnedFlow[0])
        Assert.assertEquals(MainViewModel.Operation.HideLoading, returnedFlow[1])
        Assert.assertEquals(message, (returnedFlow[2] as MainViewModel.Operation.ApiError).message)
//        Assert.assertEquals(MainViewModel.Operation.ApiError, returnedFlow)
    }

}
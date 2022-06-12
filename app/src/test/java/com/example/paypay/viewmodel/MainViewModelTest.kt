package com.example.paypay.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.paypay.repository.MainRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest {

    lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: MainRepository

    // for live data testing
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

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
    fun `verify hide loading is initiated whenever called getCurrencies`() = runBlockingTest {
        viewModel.getCurrencies()
        Assert.assertNotNull(viewModel.showHideLoadingLiveData.value)
        Assert.assertEquals(true, viewModel.showHideLoadingLiveData.value)
        Assert.assertEquals(false, viewModel.showHideLoadingLiveData.value)

    }

}
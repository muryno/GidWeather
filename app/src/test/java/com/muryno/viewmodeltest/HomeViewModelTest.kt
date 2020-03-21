package com.muryno.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.muryno.model.CurrentWeatherData
import com.muryno.ui.viewmodel.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    private val observer: Observer<CurrentWeatherData> = MockitoUtils().mock()


    @Before
    fun before() {
        viewModel = HomeViewModel()
        viewModel.getWeather?.observeForever(observer)
    }


    @Test
    fun fetchUser_ShouldReturnUser() {
        val expectedUser = CurrentWeatherData()

        viewModel.getWeather

        val captor = ArgumentCaptor.forClass(CurrentWeatherData::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(expectedUser, value)
        }
    }


}

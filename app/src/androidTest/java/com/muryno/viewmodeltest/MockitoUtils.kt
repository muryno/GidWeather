package com.muryno.viewmodeltest

import org.mockito.Mockito

class MockitoUtils {
    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}
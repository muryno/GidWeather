package com.muryno.ui.view

interface LoadingView {

    fun loadingFailed(msg: String)
    fun loadingSuccessful(msg: String)
    fun loadingStart()
}
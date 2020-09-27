package kr.co.bakeapplication.viewhandlers

interface BaseActivityHandler {
    fun startLoading()
    fun endLoading()
    fun onError(throwable: Throwable)
}
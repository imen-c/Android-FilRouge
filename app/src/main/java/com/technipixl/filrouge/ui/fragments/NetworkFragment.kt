package com.technipixl.filrouge.ui.fragments

interface NetworkFragment<T> {
    fun loadData()
    fun displayError()
    fun displayResults(response: T?)
    fun displayLoader()
    fun hideLoader()
}
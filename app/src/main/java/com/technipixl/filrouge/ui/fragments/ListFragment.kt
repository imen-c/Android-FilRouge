package com.technipixl.filrouge.ui.fragments

interface ListFragment<T, A> {
    var adapter: A
    fun setupRecyclerView(models: List<T>)
    fun displayDetail(model: T)
}
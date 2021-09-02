package com.technipixl.filrouge.ui.adapters

interface Bindable<M> {
    fun bind(model: M)
    fun unbind()
}
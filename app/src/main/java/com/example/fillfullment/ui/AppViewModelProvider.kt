package com.example.fillfullment.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fillfullment.ui.order.EditOrderViewmodel
import com.example.fillfullment.ui.order.HomeViewmodel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewmodel()
        }
        initializer {
            EditOrderViewmodel(
                this.createSavedStateHandle()
            )
        }
    }
}
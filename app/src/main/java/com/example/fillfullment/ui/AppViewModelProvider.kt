package com.example.fillfullment.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fillfullment.HomeViewmodel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewmodel(
                this.createSavedStateHandle()
            )
        }
    }
}
package com.cardona.musicdemo.di.viewModelInjection

import androidx.lifecycle.ViewModelProvider
import com.cardona.musicdemo.di.viewModelInjection.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
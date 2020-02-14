package com.cardona.musicdemo.di.mainActivityInjection

import androidx.lifecycle.ViewModel
import com.cardona.musicdemo.di.viewModelInjection.ViewModelKey
import com.cardona.musicdemo.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel

}
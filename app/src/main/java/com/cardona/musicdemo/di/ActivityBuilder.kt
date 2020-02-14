package com.cardona.musicdemo.di

import com.cardona.musicdemo.di.mainActivityInjection.MainFragmentBuilder
import com.cardona.musicdemo.di.mainActivityInjection.MainViewModelModule
import com.cardona.musicdemo.view.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder{

    @ContributesAndroidInjector(modules = [MainFragmentBuilder::class, MainViewModelModule::class])
    abstract fun bindsMainActivity(): MainActivity

}
package com.cardona.musicdemo.di.mainActivityInjection

import com.cardona.musicdemo.view.fragments.PlayListFragment
import com.cardona.musicdemo.view.fragments.UserInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder{

    @ContributesAndroidInjector
    abstract fun providesUserFragment(): UserInfoFragment

    @ContributesAndroidInjector
    abstract fun providesPlayListFragment(): PlayListFragment

}
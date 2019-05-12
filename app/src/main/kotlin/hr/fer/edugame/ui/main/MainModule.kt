package hr.fer.edugame.ui.main

import dagger.Binds
import dagger.Module

@Module
abstract class MainModule {

    @Binds
    abstract fun bindMainActivity(activity: MainActivity): MainView
}
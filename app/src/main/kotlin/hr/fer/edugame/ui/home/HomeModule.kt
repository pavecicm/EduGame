package hr.fer.edugame.ui.home

import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindView(homeFragment: HomeFragment): HomeView
}
package hr.fer.edugame.ui.home

import dagger.Binds
import dagger.Module
import hr.fer.edugame.di.modules.FirebaseModule

@Module(includes = [FirebaseModule::class])
abstract class HomeModule {

    @Binds
    abstract fun bindView(homeFragment: HomeFragment): HomeView
}
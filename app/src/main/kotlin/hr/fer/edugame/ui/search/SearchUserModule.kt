package hr.fer.edugame.ui.search

import dagger.Binds
import dagger.Module

@Module
abstract class SearchUserModule {

    @Binds
    abstract fun bindView(activity: SearchUserActivity): SearchUserView
}
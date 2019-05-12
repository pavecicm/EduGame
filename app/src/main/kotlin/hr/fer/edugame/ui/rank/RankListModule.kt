package hr.fer.edugame.ui.rank

import dagger.Binds
import dagger.Module

@Module
abstract class RankListModule {

    @Binds
    abstract fun bindView(fragment: RankListFragment): RankListView
}
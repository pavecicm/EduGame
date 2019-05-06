package hr.fer.edugame.ui.rank

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RankListModule {

    @Binds
    abstract fun bindView(fragment: RankListFragment): RankListView
}
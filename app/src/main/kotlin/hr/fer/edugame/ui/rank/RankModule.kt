package hr.fer.edugame.ui.rank

import dagger.Binds
import dagger.Module

@Module
abstract class RankModule {

    @Binds
    abstract fun bindView(fragment: RankFragment): RankView
}
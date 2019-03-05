package hr.fer.edugame.ui.numbers

import dagger.Binds
import dagger.Module

@Module
abstract class NumbersModule {

    @Binds
    abstract fun bindNumbersFragment(fragment: NumbersFragment): NumbersView
}
package hr.fer.edugame.ui.letters

import dagger.Binds
import dagger.Module

@Module
abstract class LettersModule {

    @Binds
    abstract fun bindLettersFragment(fragment: LettersFragment): LettersView
}
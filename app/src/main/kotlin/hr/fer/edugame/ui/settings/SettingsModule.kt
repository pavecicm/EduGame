package hr.fer.edugame.ui.settings

import dagger.Binds
import dagger.Module

@Module
abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsFragment(fragment: SettingsFragment): SettingsView
}
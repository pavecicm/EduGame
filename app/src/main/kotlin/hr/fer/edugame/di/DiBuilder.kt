package hr.fer.edugame.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hr.fer.edugame.ui.MainActivity
import hr.fer.edugame.ui.home.HomeFragment
import hr.fer.edugame.ui.home.HomeModule
import hr.fer.edugame.ui.home.info.InfoFragment
import hr.fer.edugame.ui.letters.LettersFragment
import hr.fer.edugame.ui.letters.LettersModule
import hr.fer.edugame.ui.numbers.NumbersFragment
import hr.fer.edugame.ui.numbers.NumbersModule
import hr.fer.edugame.ui.settings.SettingsFragment
import hr.fer.edugame.ui.settings.SettingsModule

@Module
abstract class DiBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [LettersModule::class])
    abstract fun bindLettersFragment(): LettersFragment

    @ContributesAndroidInjector(modules = [NumbersModule::class])
    abstract fun bindNumbersFragment(): NumbersFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun bindSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun bindInfoFragment(): InfoFragment
}
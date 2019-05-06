package hr.fer.edugame.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hr.fer.edugame.ui.main.MainActivity
import hr.fer.edugame.ui.home.HomeFragment
import hr.fer.edugame.ui.home.HomeModule
import hr.fer.edugame.ui.home.info.InfoFragment
import hr.fer.edugame.ui.letters.LettersFragment
import hr.fer.edugame.ui.letters.LettersModule
import hr.fer.edugame.ui.login.LoginActivity
import hr.fer.edugame.ui.login.LoginModule
import hr.fer.edugame.ui.main.MainModule
import hr.fer.edugame.ui.numbers.NumbersFragment
import hr.fer.edugame.ui.numbers.NumbersModule
import hr.fer.edugame.ui.rank.RankListFragment
import hr.fer.edugame.ui.search.SearchUserActivity
import hr.fer.edugame.ui.search.SearchUserModule
import hr.fer.edugame.ui.settings.SettingsFragment
import hr.fer.edugame.ui.settings.SettingsModule

@Module
abstract class DiBuilder {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SearchUserModule::class])
    abstract fun bindSearchUserActivity(): SearchUserActivity

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

    @ContributesAndroidInjector
    abstract fun bindRankListFragmet(): RankListFragment
}
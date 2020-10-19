package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.daggercore.di.components.ApplicationComponent
import ir.omidtaheri.mainpage.di.modules.MainModule
import ir.omidtaheri.mainpage.ui.MainFragment.MainFragment

@Component(dependencies = [ApplicationComponent::class], modules = [MainModule::class])
interface MainComponent {

    fun inject(fragment: MainFragment)
}

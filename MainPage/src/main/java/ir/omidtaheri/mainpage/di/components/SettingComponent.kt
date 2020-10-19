package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.mainpage.di.modules.SettingModule
import ir.omidtaheri.daggercore.di.components.ApplicationComponent

@Component(dependencies = [ApplicationComponent::class], modules = [SettingModule::class])
interface SettingComponent {

    // fun inject(fragment: SettingFragment)
}

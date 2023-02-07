package com.loves.superawesomeapp.dagger

import com.loves.superawesomeapp.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, AppModule::class, FacadeModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}
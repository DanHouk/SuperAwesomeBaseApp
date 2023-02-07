package com.loves.superawesomeapp.dagger

import com.loves.superawesomeapp.facade.NetworkFacade
import com.loves.superawesomeapp.network.NetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FacadeModule {
    @Provides
    @Singleton
    fun provideNetworkFacade(networkService: NetworkService): NetworkFacade =
        NetworkFacade(networkService)
}
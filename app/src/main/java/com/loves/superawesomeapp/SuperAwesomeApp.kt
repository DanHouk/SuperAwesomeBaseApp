package com.loves.superawesomeapp

import android.app.Application
import com.loves.superawesomeapp.dagger.*
import io.reactivex.plugins.RxJavaPlugins

open class SuperAwesomeApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = createAppComponent()
        initRxJava()
    }

    private fun createAppComponent(): AppComponent =
        DaggerAppComponent
            .builder()
            .facadeModule(FacadeModule())
            .netModule(NetModule())
            .appModule(AppModule(this))
            .build()

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler { e: Throwable? -> }
    }
}
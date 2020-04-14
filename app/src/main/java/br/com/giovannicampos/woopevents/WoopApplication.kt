package br.com.giovannicampos.woopevents

import android.app.Application
import br.com.giovannicampos.woopevents.base.di.getBaseModules
import br.com.giovannicampos.woopevents.events.di.getEventsModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class WoopApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@WoopApplication)
            modules(getAppModules())
        }
    }

    private fun getAppModules(): List<Module> = mutableListOf<Module>().also {
        it.addAll(getBaseModules())
        it.addAll(getEventsModules())
    }
}
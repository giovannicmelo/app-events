package br.com.giovannicampos.appevents

import android.app.Application
import br.com.giovannicampos.core.di.getCoreModules
import br.com.giovannicampos.events.di.getEventsModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class EventsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@EventsApplication)
            modules(getAppModules())
        }
    }

    private fun getAppModules(): List<Module> = mutableListOf<Module>().also {
        it.addAll(getCoreModules())
        it.addAll(getEventsModules())
    }
}
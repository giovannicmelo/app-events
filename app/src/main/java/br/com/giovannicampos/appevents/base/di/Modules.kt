package br.com.giovannicampos.appevents.base.di

import br.com.giovannicampos.appevents.base.command.CommandInjector
import br.com.giovannicampos.appevents.base.command.providers.CommandProvider
import org.koin.core.module.Module
import org.koin.dsl.module

private val commandModule: Module = module(override = true) {

    single<CommandProvider> { CommandInjector }
}

fun getBaseModules(): List<Module> = listOf(commandModule)
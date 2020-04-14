package br.com.giovannicampos.woopevents.base.di

import br.com.giovannicampos.woopevents.base.command.CommandInjector
import br.com.giovannicampos.woopevents.base.command.providers.CommandProvider
import org.koin.core.module.Module
import org.koin.dsl.module

private val commandModule: Module = module(override = true) {

    single<CommandProvider> { CommandInjector }
}

fun getBaseModules(): List<Module> = listOf(commandModule)
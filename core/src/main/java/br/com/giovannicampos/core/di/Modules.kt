package br.com.giovannicampos.core.di

import br.com.giovannicampos.core.command.CommandInjector
import br.com.giovannicampos.core.command.interfaces.CommandProvider
import org.koin.core.module.Module
import org.koin.dsl.module

private val commandModule: Module = module(override = true) {

    single<CommandProvider> { CommandInjector }
}

fun getCoreModules(): List<Module> = listOf(commandModule)
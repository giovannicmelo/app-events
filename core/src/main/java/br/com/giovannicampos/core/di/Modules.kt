package br.com.giovannicampos.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

private val coreModules: Module = module(override = true) {}

fun getCoreModules(): List<Module> = listOf(coreModules)
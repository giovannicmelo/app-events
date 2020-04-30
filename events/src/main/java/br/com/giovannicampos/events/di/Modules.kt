package br.com.giovannicampos.events.di

import br.com.giovannicampos.core.data.network.ServiceClient
import br.com.giovannicampos.events.data.api.EventsApi
import br.com.giovannicampos.events.data.contracts.EventsDataSource
import br.com.giovannicampos.events.data.contracts.EventsRepository
import br.com.giovannicampos.events.data.remote.EventsRemoteDataSource
import br.com.giovannicampos.events.data.repository.EventsRepositoryImpl
import br.com.giovannicampos.events.viewmodels.EventDetailsViewModel
import br.com.giovannicampos.events.viewmodels.EventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val eventsModules: Module = module {

    single { ServiceClient.getServiceClient(apiClass = EventsApi::class.java) }

    factory<EventsDataSource.Remote> { EventsRemoteDataSource(api = get()) }

    factory<EventsRepository> { EventsRepositoryImpl(dataSource = get()) }

    viewModel { EventsViewModel(repository = get(), commandProvider = get()) }

    viewModel { EventDetailsViewModel(repository = get(), commandProvider = get()) }
}

fun getEventsModules(): List<Module> = listOf(eventsModules)
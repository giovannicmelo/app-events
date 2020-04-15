package br.com.giovannicampos.woopevents.events.di

import br.com.giovannicampos.woopevents.base.data.services.ServiceClient
import br.com.giovannicampos.woopevents.events.data.api.EventsApi
import br.com.giovannicampos.woopevents.events.data.contracts.EventsDataSource
import br.com.giovannicampos.woopevents.events.data.contracts.EventsRepository
import br.com.giovannicampos.woopevents.events.data.remote.EventsRemoteDataSource
import br.com.giovannicampos.woopevents.events.data.repository.EventsRepositoryImpl
import br.com.giovannicampos.woopevents.events.ui.viewmodels.EventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val eventsModules: Module = module {

    single { ServiceClient.getServiceClient(apiClass = EventsApi::class.java) }

    factory<EventsDataSource.Remote> { EventsRemoteDataSource(api = get()) }

    factory<EventsRepository> { EventsRepositoryImpl(dataSource = get()) }

    viewModel { EventsViewModel(repository = get(), commandProvider = get()) }
}

fun getEventsModules(): List<Module> = listOf(eventsModules)
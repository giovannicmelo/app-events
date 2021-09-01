package br.com.giovannicampos.events.di

import br.com.giovannicampos.core.data.network.ServiceClient
import br.com.giovannicampos.events.data.EventDataSource
import br.com.giovannicampos.events.data.EventRepository
import br.com.giovannicampos.events.data.EventRepositoryContract
import br.com.giovannicampos.events.framework.RetrofitEventDataSource
import br.com.giovannicampos.events.framework.api.EventsApi
import br.com.giovannicampos.events.interactors.DoCheckIn
import br.com.giovannicampos.events.interactors.FetchAllEvents
import br.com.giovannicampos.events.interactors.GetEventDetails
import br.com.giovannicampos.events.interactors.UseCase
import br.com.giovannicampos.events.presentation.viewmodels.EventDetailsViewModel
import br.com.giovannicampos.events.presentation.viewmodels.EventsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val eventsModules: Module = module {

    single { ServiceClient.getServiceClient(apiClass = EventsApi::class.java) }

    /**
     * Data access
     */
    factory<EventDataSource> { RetrofitEventDataSource(api = get()) }
    factory<EventRepositoryContract> { EventRepository(dataSource = get()) }

    /**
     * Use cases
     */
    factory<UseCase> { DoCheckIn(repository = get()) }
    factory<UseCase> { FetchAllEvents(repository = get()) }
    factory<UseCase> { GetEventDetails(repository = get()) }

    /**
     * View models
     */
    viewModel { EventsListViewModel(fetchAllEvents = get()) }
    viewModel { EventDetailsViewModel(getEventDetails = get(), doCheckIn = get()) }
}

fun getEventsModules(): List<Module> = listOf(eventsModules)
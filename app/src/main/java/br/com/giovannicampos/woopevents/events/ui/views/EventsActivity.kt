package br.com.giovannicampos.woopevents.events.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.giovannicampos.woopevents.databinding.ActivityEventsBinding
import br.com.giovannicampos.woopevents.events.ui.viewmodels.EventsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityEventsBinding

    private val viewModel by viewModel<EventsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEventsBinding.inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
            setSupportActionBar(binding.toolbar)
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getEvents()

        viewModel.command.observe(this, Observer { command ->
            when (command) {
                is EventsViewModel.Command.ShowEmptyListMessage -> {

                }
                is EventsViewModel.Command.ShowApiErrorMessage -> {

                }
                is EventsViewModel.Command.ShowEvents -> {

                }
            }
        })

        viewModel.viewState.observe(this, Observer { viewState ->
            viewState?.let { render(viewState) }
        })
    }

    private fun render(viewState: EventsViewModel.ViewState) {
        if (viewState.isLoading) {

        } else {

        }
    }
}

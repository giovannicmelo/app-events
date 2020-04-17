package br.com.giovannicampos.appevents.events.ui.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import br.com.giovannicampos.appevents.R
import br.com.giovannicampos.appevents.base.utils.isConnected
import br.com.giovannicampos.appevents.databinding.ActivityEventsBinding
import br.com.giovannicampos.appevents.events.ui.viewmodels.EventsViewModel
import br.com.giovannicampos.appevents.events.ui.views.adapters.EventsAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsActivity : AppCompatActivity() {

    companion object {
        const val EVENT_ID_EXTRA = "event_id"
    }

    private lateinit var viewBinding: ActivityEventsBinding

    private val viewModel by viewModel<EventsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEventsBinding.inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
        }

        setSupportActionBar(viewBinding.toolbar)

        setupObservables()
        loadEventList()
    }

    private fun loadEventList() {
        if (isConnected()) {
            viewModel.getEvents()
        } else {
            viewModel.showNoConnectionNetwork()
        }
    }

    private fun setupObservables() {
        viewModel.command.observe(this, Observer { command ->
            when (command) {
                is EventsViewModel.Command.ShowEmptyListMessage -> {
                    showSnackMessage(getString(R.string.empty_list))
                }
                is EventsViewModel.Command.ShowApiErrorMessage -> {
                    showSnackMessage(getString(R.string.try_later))
                }
                is EventsViewModel.Command.ShowEvents -> {
                    val adapter =
                        EventsAdapter(
                            command.events
                        ) {
                            goToEventDetails(it.id)
                        }
                    viewBinding.rvEvents.adapter = adapter
                }
                is EventsViewModel.Command.ShowExceptionMessage -> {
                    showSnackMessage(command.message)
                }
                is EventsViewModel.Command.ShowNoConnectionMessage -> {
                    showSnackMessage(getString(R.string.no_connection))
                }
            }
        })

        viewModel.viewState.observe(this, Observer { viewState ->
            viewState?.let { render(viewState) }
        })
    }

    private fun goToEventDetails(eventId: String) {
        val newIntent = Intent(this, EventDetailActivity::class.java).also {
            it.putExtra(EVENT_ID_EXTRA, eventId)
        }

        startActivity(newIntent)
    }

    private fun render(viewState: EventsViewModel.ViewState) {
        if (viewState.isLoading) {
            viewBinding.progressCircular.visibility = View.VISIBLE
            viewBinding.rvEvents.visibility = View.GONE
        } else {
            viewBinding.progressCircular.visibility = View.GONE
            viewBinding.rvEvents.visibility = View.VISIBLE
        }
    }

    private fun showSnackMessage(message: String) {
        val snack = Snackbar.make(
            viewBinding.root,
            message,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction(getString(R.string.ok)) { snack.dismiss() }.show()
    }
}

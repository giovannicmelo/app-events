package br.com.giovannicampos.events.presentation.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.giovannicampos.core.ui.Loader
import br.com.giovannicampos.core.utils.isConnected
import br.com.giovannicampos.events.R
import br.com.giovannicampos.events.databinding.ActivityEventsBinding
import br.com.giovannicampos.events.framework.utils.State
import br.com.giovannicampos.events.presentation.viewmodels.EventsListViewModel
import br.com.giovannicampos.events.presentation.views.adapters.EventsAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsActivity : AppCompatActivity() {

    companion object {
        const val EVENT_ID_EXTRA = "event_id"
    }

    private lateinit var viewBinding: ActivityEventsBinding

    private val viewModel: EventsListViewModel by viewModel()

    private val loader: Loader by lazy { Loader(this, this.window?.decorView as ViewGroup) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEventsBinding.inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
        }

        setSupportActionBar(viewBinding.toolbar)
        loadEventList()
        configureObservers()
    }

    private fun configureObservers() {
        viewModel.snackMessage.observe(this, { message ->
            showSnackMessage(message)
        })
    }

    private fun loadEventList() {
        if (isConnected()) {
            viewModel.getEventsList().observe(this, { state ->
                when (state) {
                    is State.LoadingState -> {
                        loader.start()
                    }
                    is State.DataState -> {
                        loader.stop()
                        if (state.data.isNotEmpty()) {
                            viewBinding.rvEvents.adapter = EventsAdapter(state.data) { e ->
                                goToEventDetails(e.id)
                            }
                        } else {
                            viewModel.showSnackMessage(getString(R.string.empty_list))
                        }
                    }
                    is State.ErrorState -> {
                        loader.stop()
                        viewModel.showSnackMessage(state.exception.localizedMessage!!)
                    }
                }
            })
        } else {
            viewModel.showSnackMessage(getString(R.string.no_connection))
        }
    }

    private fun goToEventDetails(eventId: String) {
        Intent(this, EventDetailActivity::class.java).also {
            it.putExtra(EVENT_ID_EXTRA, eventId)
            startActivity(it)
        }
    }

    private fun showSnackMessage(message: String) {
        Snackbar.make(viewBinding.root, message, Snackbar.LENGTH_INDEFINITE).also { snack ->
            snack.setAction(getString(R.string.ok)) { snack.dismiss() }.show()
        }
    }
}

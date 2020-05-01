package br.com.giovannicampos.events.ui.views.activities

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.giovannicampos.core.utils.getAddresses
import br.com.giovannicampos.core.utils.timestampToDateFull
import br.com.giovannicampos.core.utils.toCurrency
import br.com.giovannicampos.events.R
import br.com.giovannicampos.events.data.models.Event
import br.com.giovannicampos.events.databinding.ActivityEventDetailBinding
import br.com.giovannicampos.events.databinding.DialogCheckInBinding
import br.com.giovannicampos.events.ui.viewmodels.EventDetailsViewModel
import br.com.giovannicampos.events.ui.views.adapters.CouponsAdapter
import br.com.giovannicampos.events.ui.views.adapters.PeopleAdapter
import br.com.giovannicampos.events.utils.isValidEmail
import br.com.giovannicampos.events.utils.isValidName
import coil.api.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDetailActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityEventDetailBinding
    private lateinit var dialogViewBinding: DialogCheckInBinding

    private val viewModel by viewModel<EventDetailsViewModel>()
    private val eventIdExtra: String by lazy {
        intent.getStringExtra(EventsActivity.EVENT_ID_EXTRA)
    }

    private var dialogView: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEventDetailBinding.inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
        }

        setupToolbar(viewBinding.toolbarCollapsing)

        setupObservables()
        setupListeners()
        loadEventDetails()
    }

    private fun setupListeners() {
        viewBinding.btCheckIn.setOnClickListener { viewModel.openCheckInModal() }
        viewBinding.btShare.setOnClickListener { viewModel.openShareContentEvent() }
    }

    private fun loadEventDetails() {
        if (intent.hasExtra(EventsActivity.EVENT_ID_EXTRA)) {
            viewModel.getEventById(eventIdExtra)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setupObservables() {
        viewModel.command.observe(this, Observer { command ->
            when (command) {
                is EventDetailsViewModel.Command.ShowApiErrorMessage -> {
                    showSnackMessage(getString(R.string.try_later))
                }
                is EventDetailsViewModel.Command.ShowEventDetails -> {
                    subscribeUi(command.event)
                }
                is EventDetailsViewModel.Command.ShowExceptionMessage -> {
                    showSnackMessage(command.message)
                }
                is EventDetailsViewModel.Command.ShowNoConnectionMessage -> {
                    showSnackMessage(getString(R.string.no_connection))
                }
                is EventDetailsViewModel.Command.OpenCheckInFormModal -> {
                    showCheckInModal()
                }
                is EventDetailsViewModel.Command.ShareContentEvent -> {
                    shareContent(viewBinding.tvEventDetailDescription.text.toString())
                }
                is EventDetailsViewModel.Command.CheckInAccomplished -> {
                    showSnackMessage(getString(R.string.check_in_successfully))
                    dialogView?.dismiss()
                }
            }
        })

        viewModel.viewState.observe(this, Observer { viewState ->
            viewState?.let { render(viewState) }
        })
    }

    private fun subscribeUi(event: Event) {
        viewBinding.run {
            ivEventDetailsImage.load(event.image) {
                crossfade(true)
                error(R.drawable.layer_placeholder)
            }
            tvEventDetailTitle.text = event.title
            tvEventDetailDate.text = event.date.timestampToDateFull()
            tvEventDetailDescription.text = event.description
            tvEventDetailPrice.text = getString(R.string.currency_with_space, event.price.toCurrency("#,###.00"))

            rvPeople.adapter =
                PeopleAdapter(
                    event.listPersons
                )

            rvCoupons.adapter = CouponsAdapter(event.listCoupons)
            rvCoupons.layoutManager = LinearLayoutManager(
                this@EventDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvCoupons.setHasFixedSize(true)

            val addresses: List<Address> = this@EventDetailActivity.getAddresses(event.latitude, event.longitude)
            tvEventDetailAddress.text = addresses[0].getAddressLine(0)
        }

        setupMaps(event)
    }

    private fun render(viewState: EventDetailsViewModel.ViewState) {
        if (viewState.isLoading) {
            viewBinding.progressCircular.visibility = View.VISIBLE
            viewBinding.llEventDetailsContent.visibility = View.GONE
        } else {
            viewBinding.progressCircular.visibility = View.GONE
            viewBinding.llEventDetailsContent.visibility = View.VISIBLE
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

    private fun shareContent(content: String) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.share)))
    }

    private fun showCheckInModal() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_check_in, null)
        dialogViewBinding = DialogCheckInBinding.bind(view)

        val viewBindingApplied = dialogViewBinding.apply {
            btCheckInConfirm.setOnClickListener {
                validateFields(etPersonName.text.toString(), etPersonEmail.text.toString())
            }
        }

        val dialogBuilder = AlertDialog.Builder(this).setView(viewBindingApplied.root)
        dialogView = dialogBuilder.create()
        dialogView?.show()
    }

    private fun validateFields(name: String, email: String) {
        if (!name.isValidName()) {
            dialogViewBinding.tlPersonName.error = getString(R.string.invalid_name)
            return
        } else {
            dialogViewBinding.tlPersonName.error = null
        }

        if (!email.isValidEmail()) {
            dialogViewBinding.tlPersonEmail.error = getString(R.string.invalid_email)
            return
        } else {
            dialogViewBinding.tlPersonEmail.error = null
        }

        viewModel.doCheckIn(eventIdExtra, name, email)
    }

    private fun setupMaps(event: Event) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment

        mapFragment.getMapAsync { map ->
            val location = LatLng(event.latitude, event.longitude)
            val addresses = getAddresses(location.latitude, location.longitude)

            map.addMarker(
                MarkerOptions().position(location)
                    .title(event.title)
                    .snippet(getString(R.string.neighborhood, addresses[0].subLocality))
            )
            map.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))
        }
    }
}

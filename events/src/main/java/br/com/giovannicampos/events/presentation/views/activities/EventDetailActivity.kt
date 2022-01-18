package br.com.giovannicampos.events.presentation.views.activities

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.giovannicampos.core.ui.Loader
import br.com.giovannicampos.core.utils.getAddresses
import br.com.giovannicampos.core.utils.isConnected
import br.com.giovannicampos.core.utils.timestampToDateFull
import br.com.giovannicampos.core.utils.toCurrency
import br.com.giovannicampos.events.R
import br.com.giovannicampos.events.databinding.ActivityEventDetailBinding
import br.com.giovannicampos.events.databinding.DialogCheckInBinding
import br.com.giovannicampos.events.domain.entities.Event
import br.com.giovannicampos.events.framework.utils.State
import br.com.giovannicampos.events.presentation.viewmodels.EventDetailsViewModel
import br.com.giovannicampos.events.presentation.views.adapters.CouponsAdapter
import br.com.giovannicampos.events.presentation.views.adapters.PeopleAdapter
import br.com.giovannicampos.events.presentation.utils.isValidEmail
import br.com.giovannicampos.events.presentation.utils.isValidName
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

    private val viewModel: EventDetailsViewModel by viewModel()
    private val eventIdExtra: String by lazy { intent.getStringExtra(EventsActivity.EVENT_ID_EXTRA) ?: "" }
    private val loader: Loader by lazy { Loader(this, this.window?.decorView as ViewGroup) }

    private var dialogView: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityEventDetailBinding.inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
        }

        setupToolbar(viewBinding.toolbarCollapsing)

        configureObservers()
        configureListeners()
        loadEventDetails()
    }

    private fun configureListeners() {
        viewBinding.btCheckIn.setOnClickListener { showCheckInModal() }
        viewBinding.btShare.setOnClickListener { shareContent(viewBinding.tvEventDetailDescription.text.toString()) }
    }

    private fun loadEventDetails() {
        if (isConnected()) {
            if (intent.hasExtra(EventsActivity.EVENT_ID_EXTRA)) {
                viewModel.getDetails(eventIdExtra).observe(this, { state ->
                    when (state) {
                        is State.LoadingState -> {
                            loader.start()
                            viewBinding.llEventDetailsContent.visibility = View.GONE
                        }
                        is State.DataState -> {
                            loader.stop()
                            subscribeUi(state.data)
                        }
                        is State.ErrorState -> {
                            loader.stop()
                            viewModel.showSnackMessage(state.exception.localizedMessage!!)
                        }
                    }
                })
            }
        } else {
            viewModel.showSnackMessage(getString(R.string.no_connection))
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

    private fun configureObservers() {
        viewModel.snackMessage.observe(this, { message ->
            showSnackMessage(message)
        })
    }

    private fun subscribeUi(event: Event) {
        viewBinding.run {
            llEventDetailsContent.visibility = View.VISIBLE
            ivEventDetailsImage.load(event.image) {
                crossfade(true)
                error(R.drawable.layer_placeholder)
            }
            tvEventDetailTitle.text = event.title
            tvEventDetailDate.text = event.date.timestampToDateFull()
            tvEventDetailDescription.text = event.description
            tvEventDetailPrice.text =
                getString(R.string.currency_with_space, event.price.toCurrency("#,###.00"))

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

            val addresses: List<Address> =
                this@EventDetailActivity.getAddresses(event.latitude, event.longitude)
            tvEventDetailAddress.text = addresses[0].getAddressLine(0)
        }

        setupMaps(event)
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

        doCheckIn(eventIdExtra, name, email)
    }

    private fun doCheckIn(eventId: String, name: String, email: String) {
        viewModel.doCheckIn(eventId, name, email).observe(this, { state ->
            when (state) {
                is State.DataState -> {
                    viewModel.showSnackMessage(getString(R.string.check_in_successfully))
                    dialogView?.dismiss()
                }
                is State.ErrorState -> {
                    state.exception.printStackTrace()
                    viewModel.showSnackMessage(getString(R.string.try_later))
                }
                else -> {
                }
            }
        })
    }

    private fun setupMaps(event: Event) {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment

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

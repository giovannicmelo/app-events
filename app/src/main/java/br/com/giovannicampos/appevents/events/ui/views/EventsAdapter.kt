package br.com.giovannicampos.appevents.events.ui.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.giovannicampos.appevents.R
import br.com.giovannicampos.appevents.base.utils.dpToPx
import br.com.giovannicampos.appevents.base.utils.timestampToDayOfMonth
import br.com.giovannicampos.appevents.base.utils.timestampToShortMonth
import br.com.giovannicampos.appevents.base.utils.timestampToYear
import br.com.giovannicampos.appevents.databinding.ItemEventBinding
import br.com.giovannicampos.appevents.events.data.models.Event
import coil.api.load
import coil.transform.RoundedCornersTransformation

private const val DEFAULT_RADIUS = 8

class EventsAdapter(
    private val events: List<Event>,
    private val actionItem: (Event) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemEventBinding.inflate(inflater, parent, false)
        return EventsViewHolder(viewBinding, actionItem)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    class EventsViewHolder(
        private val viewBinding: ItemEventBinding,
        private val actionItem: (Event) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(event: Event) {
            val context = viewBinding.root.context

            viewBinding.run {
                ivEventImage.load(event.image) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(context.dpToPx(DEFAULT_RADIUS)))
                }
                tvEventTitle.text = event.title
                tvEventDay.text = context.getString(
                    R.string.day_and_month,
                    event.date.timestampToDayOfMonth(),
                    event.date.timestampToShortMonth()
                )
                tvEventYear.text = event.date.timestampToYear()
                vEvent.setOnClickListener {
                    actionItem.invoke(event)
                }
            }
        }
    }
}
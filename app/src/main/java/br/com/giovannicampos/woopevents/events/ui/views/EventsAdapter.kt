package br.com.giovannicampos.woopevents.events.ui.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.giovannicampos.woopevents.R
import br.com.giovannicampos.woopevents.base.utils.timestampToDayOfMonth
import br.com.giovannicampos.woopevents.base.utils.timestampToShortMonth
import br.com.giovannicampos.woopevents.base.utils.timestampToYear
import br.com.giovannicampos.woopevents.base.utils.toPx
import br.com.giovannicampos.woopevents.databinding.ItemEventBinding
import br.com.giovannicampos.woopevents.events.data.models.Event
import coil.api.load
import coil.transform.RoundedCornersTransformation

private const val DEFAULT_RADIUS = 8

class EventsAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemEventBinding.inflate(inflater, parent, false)
        return EventsViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    class EventsViewHolder(private val viewBinding: ItemEventBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(event: Event) {
            val context = viewBinding.root.context

            viewBinding.run {
                ivEventImage.load(event.image) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(DEFAULT_RADIUS.toPx()))
                }
                tvEventTitle.text = event.title
                tvEventDay.text = context.getString(
                    R.string.day_and_month,
                    event.date.timestampToDayOfMonth(),
                    event.date.timestampToShortMonth()
                )
                tvEventYear.text = event.date.timestampToYear()
            }
        }
    }
}
package br.com.giovannicampos.events.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.giovannicampos.events.databinding.ItemPersonBinding
import br.com.giovannicampos.events.domain.Person
import coil.api.load
import coil.transform.CircleCropTransformation

class PeopleAdapter(private val people: List<Person>)
    : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemPersonBinding.inflate(inflater, parent, false)
        return PeopleViewHolder(
            viewBinding
        )
    }

    override fun getItemCount(): Int = people.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(people[position])
    }

    class PeopleViewHolder(private val viewBinding: ItemPersonBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(person: Person) {
            viewBinding.run {
                ivPersonPhoto.load(person.picture) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvPersonName.text = person.name
                if (person.name.isNotEmpty()) tvPersonInitial.text = person.name[0].toUpperCase().toString()
            }
        }
    }
}
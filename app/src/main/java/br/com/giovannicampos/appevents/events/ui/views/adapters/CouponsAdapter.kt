package br.com.giovannicampos.appevents.events.ui.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.giovannicampos.appevents.R
import br.com.giovannicampos.appevents.databinding.ItemCouponBinding
import br.com.giovannicampos.appevents.databinding.ItemPersonBinding
import br.com.giovannicampos.appevents.events.data.models.Event
import coil.api.load
import coil.transform.CircleCropTransformation

class CouponsAdapter(private val coupons: List<Event.Coupon>)
    : RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCouponBinding.inflate(inflater, parent, false)
        return CouponsViewHolder(
            viewBinding
        )
    }

    override fun getItemCount(): Int = coupons.size

    override fun onBindViewHolder(holder: CouponsViewHolder, position: Int) {
        holder.bind(coupons[position])
    }

    class CouponsViewHolder(private val viewBinding: ItemCouponBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(coupon: Event.Coupon) {
            val context = viewBinding.root.context
            viewBinding.tvCouponDiscount.text = context.getString(R.string.discount, coupon.discount)
        }
    }
}
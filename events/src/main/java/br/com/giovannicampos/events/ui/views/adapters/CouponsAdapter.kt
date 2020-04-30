package br.com.giovannicampos.events.ui.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.giovannicampos.events.R
import br.com.giovannicampos.events.data.models.Event
import br.com.giovannicampos.events.databinding.ItemCouponBinding

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

        @SuppressLint("StringFormatInvalid")
        fun bind(coupon: Event.Coupon) {
            val context = viewBinding.root.context
            viewBinding.tvCouponDiscount.text = context.getString(R.string.discount, coupon.discount)
        }
    }
}
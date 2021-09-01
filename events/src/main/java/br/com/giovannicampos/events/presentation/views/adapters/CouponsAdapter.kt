package br.com.giovannicampos.events.presentation.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.giovannicampos.events.R
import br.com.giovannicampos.events.databinding.ItemCouponBinding
import br.com.giovannicampos.events.domain.Coupon

class CouponsAdapter(private val coupons: List<Coupon>)
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
        fun bind(coupon: Coupon) {
            val context = viewBinding.root.context
            viewBinding.tvCouponDiscount.text = context.getString(R.string.discount, coupon.discount)
        }
    }
}
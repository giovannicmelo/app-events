package br.com.giovannicampos.core.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.giovannicampos.core.R

class Loader(
    context: Context,
    var viewGroup: ViewGroup?
) {
    private var active: Boolean = false
    private var layout: ConstraintLayout = ConstraintLayout(context)

    init {
        layout = View.inflate(context, R.layout.loader_view_layout, null) as ConstraintLayout
    }

    fun start() {
        if (active) return
        active = true
        layout.visibility = View.VISIBLE
        viewGroup?.addView(layout)
    }

    fun stop() {
        if (!active) return
        active = false
        layout.visibility = View.GONE
        viewGroup?.removeView(layout)
        layout.invalidate()
    }
}
@file:JvmName("ActivityDataBinding")

package br.com.giovannicampos.core.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import br.com.giovannicampos.core.R
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewDataBinding> FragmentActivity.dataBinding(): Lazy<T> = object : Lazy<T> {
    @SuppressLint("StaticFieldLeak")
    private var binding: T? = null
    override fun isInitialized(): Boolean = binding != null
    override val value: T
        get() = binding ?: bind<T>(getContentView()).also {
            it.lifecycleOwner = this@dataBinding
            binding = it
        }

    private fun FragmentActivity.getContentView(): View {
        return checkNotNull(findViewById<ViewGroup>(R.id.content).getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout res id."
        }
    }

    private fun <T : ViewDataBinding> bind(view: View): T = DataBindingUtil.bind(view)!!
}

fun <T : ViewDataBinding> activityBinding(@LayoutRes resId: Int) =
    object : ReadOnlyProperty<Activity, T> {
        @SuppressLint("StaticFieldLeak")
        private var cached: T? = null
        override operator fun getValue(thisRef: Activity, property: KProperty<*>): T {
            return cached ?: DataBindingUtil.setContentView<T>(thisRef, resId)
        }
    }
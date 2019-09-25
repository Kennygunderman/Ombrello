package com.kennygunderman.ombrello.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kennygunderman.ombrello.BR
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<out TViewModel: BaseViewModel, TBinding : ViewDataBinding> (clazz: KClass<TViewModel>) : Fragment() {

    /**
     * Layout id associated to the DataBinding object
     */
    protected abstract val resId: Int

    /**
     * ViewModel associated to the Fragment
     */
    protected val viewModel: TViewModel by viewModel(clazz)

    /**
     * Universal Binding for Fragment layout.
     *
     * Has a chance to be @Nullable, but according to @see[DataBindingUtil.setContentView]
     * Google is confident that this value should almost never be null.
     */
    protected lateinit var binding: TBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        DataBindingUtil.inflate<TBinding>(inflater, resId, container, false)
            .also {
                it.setVariable(BR.viewModel, viewModel)
                it.lifecycleOwner = this
                binding = it
            }
            .root

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }
}

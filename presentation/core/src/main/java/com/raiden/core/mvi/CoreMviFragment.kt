package com.raiden.core.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class CoreMviFragment<A : CoreAction, S : CoreState>(
    @LayoutRes private val layoutID: Int
) : Fragment() {


    abstract val mviIntent: CoreMviIntent<A, S>

    abstract fun renderState(state: S)

    abstract fun initAction(): A?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutID, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mviIntent.observableState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        val action = initAction()
        if (action != null) {
            mviIntent.dispatch(action)
        }
    }
}
package com.example.ecoproject.common.mvvm

import androidx.fragment.app.Fragment
import com.example.ecoproject.ui.progress.ProgressDialog


@Suppress("UNCHECKED_CAST")
open class BaseFragment<T : BaseActivity> : Fragment() {
    private val progress: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    protected fun showProgress() {
        progress.show()
    }

    protected fun hideProgress() {
        progress.dismiss()
    }

    protected var isProgressVisible = false
        set(value) {
            if(value) showProgress()
            else hideProgress()
            field = value
        }
        get() = progress.isShowing

    protected val baseActivity: T by lazy {
        activity as T
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progress.dismiss()
    }

    protected val appComponent by lazy {
        if (activity is BaseActivity) baseActivity.component
        else throw RuntimeException("activity is not instance of BaseActivity")
    }
}
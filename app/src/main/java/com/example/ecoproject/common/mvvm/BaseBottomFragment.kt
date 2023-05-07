package com.example.ecoproject.common.mvvm

import com.example.ecoproject.ui.progress.ProgressDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomFragment<T : BaseActivity> : BottomSheetDialogFragment() {
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
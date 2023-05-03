package com.example.ecoproject.common.mvvm

import androidx.fragment.app.Fragment


open class BaseFragment<T : BaseActivity> : Fragment() {
    protected val baseActivity: T by lazy {
        activity as T
    }
    protected val appComponent =
        if (activity is BaseActivity) baseActivity.component
        else throw RuntimeException("activity is not instance of BaseActivity")


}
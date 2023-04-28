package com.example.ecoproject.common.mvvm

import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {
    protected val appComponent =
        if (activity is BaseActivity) (activity as BaseActivity).component
        else throw RuntimeException("activity is not instance of BaseActivity")


}
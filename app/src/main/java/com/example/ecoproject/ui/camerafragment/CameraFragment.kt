package com.example.ecoproject.ui.camerafragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.CameraFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import javax.inject.Inject

class CameraFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: CameraViewModel

    private lateinit var binding: CameraFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.cameraSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CameraFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
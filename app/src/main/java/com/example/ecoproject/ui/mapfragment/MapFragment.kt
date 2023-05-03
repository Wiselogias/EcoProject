package com.example.ecoproject.ui.mapfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.MapFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import javax.inject.Inject

class MapFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: MapViewModel

    private lateinit var binding: MapFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.mapSubcomponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MapFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.points.collectOnLifeCycle(this) {

        }
    }
}
package com.example.ecoproject.ui.profilefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.ProfileFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import javax.inject.Inject

class ProfileFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: ProfileViewModel

    private lateinit var binding: ProfileFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.profileSubcomponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
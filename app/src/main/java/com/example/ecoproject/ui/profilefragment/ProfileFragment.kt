package com.example.ecoproject.ui.profilefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.ProfileFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import javax.inject.Inject

class ProfileFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: ProfileViewModel

    private lateinit var binding: ProfileFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteArticles.setOnClickListener {
            findNavController().navigate(R.id.action_action_profile_to_favoriteArticlesFragment)
        }

        binding.favoritePoints.setOnClickListener {
            findNavController().navigate(R.id.action_action_profile_to_favoritePointsFragment)
        }

        viewModel.user.collectOnLifeCycle(this) {
            binding.mobileNumber.text = it.phoneNumber
        }

        binding.signOut.setOnClickListener {
            viewModel.signOut()
        }
    }
}
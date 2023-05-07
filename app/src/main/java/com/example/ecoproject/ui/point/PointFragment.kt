package com.example.ecoproject.ui.point

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseBottomFragment
import com.example.ecoproject.databinding.PointFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.repeatOnCreated
import javax.inject.Inject

class PointFragment : BaseBottomFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: PointViewModel
    private lateinit var binding: PointFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.pointSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PointFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnCreated {
            viewModel.pointID.emit(requireArguments().getString("id", ""))
        }

        viewModel.point.collectOnLifeCycle(this) { (point, isFavorite) ->
            hideProgress()
            binding.textView.text = point.address
            binding.toolBar.menu.findItem(R.id.action_add_to_favorite).isVisible = !isFavorite
            binding.toolBar.menu.findItem(R.id.action_remove_from_favorite).isVisible = isFavorite
        }

        binding.toolBar.menu.findItem(R.id.action_add_to_favorite).setOnMenuItemClickListener {
            viewModel.addToFavorite().collectIn(lifecycleScope) { }
            true
        }

        binding.toolBar.menu.findItem(R.id.action_remove_from_favorite)
            .setOnMenuItemClickListener {
                viewModel.removeFromFavorite().collectIn(lifecycleScope) { }
                true
            }
    }
}
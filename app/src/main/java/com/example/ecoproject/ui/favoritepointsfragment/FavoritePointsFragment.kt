package com.example.ecoproject.ui.favoritepointsfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.ArticlesFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.pointsadapter.PointItemAdapter
import com.example.ecoproject.ui.utils.UIUtils.repeatOnCreated
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FavoritePointsFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: FavoriteViewModel

    private lateinit var binding: ArticlesFragmentBinding

    private val pointsAdapter by lazy {
        PointItemAdapter {
            findNavController().navigate(
                R.id.action_favoritePointsFragment_to_pointFragment,
                bundleOf("id" to it.id)
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        baseActivity.activityComponent.favoritePointsSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArticlesFragmentBinding.inflate(inflater, container, false)

        binding.articles.apply {
            adapter = pointsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.toolBar.title = getString(R.string.favorite_points)
        binding.infoView.text = getString(R.string.no_points)
        binding.floatingActionButton.isVisible = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnCreated {
            viewModel.points.collectLatest {
                pointsAdapter.submitData(it)
            }
        }

        repeatOnCreated {
            pointsAdapter.loadStateFlow.collectLatest {
                binding.progressCircular.isVisible =
                    pointsAdapter.itemCount == 0 && it.refresh is LoadState.Loading
                binding.infoView.isVisible =
                    pointsAdapter.itemCount == 0 && it.refresh is LoadState.NotLoading
            }
        }
    }
}
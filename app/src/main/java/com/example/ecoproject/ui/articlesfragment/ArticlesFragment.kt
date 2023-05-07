package com.example.ecoproject.ui.articlesfragment

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
import com.example.ecoproject.ui.articlesadapter.ArticleItemAdapter
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.repeatOnCreated
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ArticlesFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: ArticlesViewModel

    private lateinit var binding: ArticlesFragmentBinding

    private val articlesAdapter by lazy {
        ArticleItemAdapter {
            findNavController().navigate(
                R.id.action_action_articles_to_articleFragment,
                bundleOf("id" to it.id)
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.articlesSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArticlesFragmentBinding.inflate(inflater, container, false)

        binding.articles.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.toolBar.title = getString(R.string.article)
        binding.infoView.text = getString(R.string.no_articles)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnCreated {
            viewModel.articles.collectLatest {
                articlesAdapter.submitData(it)
            }
        }

        repeatOnCreated {
            articlesAdapter.loadStateFlow.collectLatest {
                binding.progressCircular.isVisible =
                    articlesAdapter.itemCount == 0 && it.refresh is LoadState.Loading
                binding.infoView.isVisible =
                    articlesAdapter.itemCount == 0 && it.refresh is LoadState.NotLoading
            }
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_action_articles_to_createArticleActivity)
        }
    }
}
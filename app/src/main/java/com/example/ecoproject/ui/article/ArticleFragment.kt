package com.example.ecoproject.ui.article

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.ArticleFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.repeatOnCreated
import com.example.ecoproject.ui.utils.UIUtils.top
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject

class ArticleFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: ArticleViewModel
    private lateinit var binding: ArticleFragmentBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.articleSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArticleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgress()

        repeatOnCreated {
            viewModel.articleID.emit(requireArguments().getString("id", ""))
        }

        viewModel.article.collectOnLifeCycle(this) { (article, isFavorite) ->
            hideProgress()
            binding.imageView.isVisible = article.imageReference.isNotEmpty()
            binding.titleView.title = article.title
            binding.textView.text = article.text
            binding.titleView.menu.findItem(R.id.action_add_to_favorite).isVisible = !isFavorite
            binding.titleView.menu.findItem(R.id.action_remove_from_favorite).isVisible = isFavorite
            if (article.imageReference.isNotEmpty()) Glide
                .with(binding.imageView)
                .load(Firebase.storage.reference.child(article.imageReference))
                .centerCrop()
                .into(binding.imageView)
        }

        binding.titleView.menu.findItem(R.id.action_add_to_favorite).setOnMenuItemClickListener {
            viewModel.addToFavorite().collectIn(lifecycleScope) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.added_to_favorite),
                    Snackbar.LENGTH_SHORT
                ).top().show()
            }
            true
        }

        binding.titleView.menu.findItem(R.id.action_remove_from_favorite)
            .setOnMenuItemClickListener {
                viewModel.removeFromFavorite().collectIn(lifecycleScope) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.removed_from_favorite),
                        Snackbar.LENGTH_SHORT
                    ).top().show()
                }
                true
            }
    }
}
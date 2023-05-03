package com.example.ecoproject.ui.articlesfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.ArticlesFragmentBinding
import com.example.ecoproject.ui.main.MainActivity
import javax.inject.Inject

class ArticlesFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: ArticlesViewModel

    private lateinit var binding: ArticlesFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.articlesSubcomponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ArticlesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
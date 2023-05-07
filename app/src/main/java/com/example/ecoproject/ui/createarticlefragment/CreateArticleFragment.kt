package com.example.ecoproject.ui.createarticlefragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.CreateArticleFragmentBinding
import com.example.ecoproject.domain.usecases.article.CreateArticleResult
import com.example.ecoproject.domain.usecases.images.ImageUploadResult
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.onTextChanged
import com.example.ecoproject.ui.utils.UIUtils.top
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class CreateArticleFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: CreateArticleViewModel
    private lateinit var binding: CreateArticleFragmentBinding

    private fun openUri(uri: Uri) {
        viewModel.uploadFile(requireContext().contentResolver.openInputStream(uri))
            .collectIn(lifecycleScope) {
                when (it) {
                    is ImageUploadResult.Success -> {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.image_upload_success),
                            Snackbar.LENGTH_SHORT
                        ).top().show()
                        Glide
                            .with(binding.articleImagePreview)
                            .load(it.bitmap)
                            .centerCrop()
                            .into(binding.articleImagePreview)
                    }

                    is ImageUploadResult.Failed -> Snackbar.make(
                        binding.root,
                        it.t.localizedMessage ?: it.t.message
                        ?: getString(R.string.something_went_wrong),
                        Snackbar.LENGTH_SHORT
                    ).top().show()
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.createArticleSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CreateArticleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleEditor.onTextChanged()
            .collectOnLifeCycle(this, Lifecycle.State.CREATED, viewModel.title)
        binding.articleTextEditor.onTextChanged()
            .collectOnLifeCycle(this, Lifecycle.State.CREATED, viewModel.text)

        viewModel.canCreateArticle.collectOnLifeCycle(this) {
            binding.publishButton.isEnabled = it
        }

        binding.publishButton.setOnClickListener {
            binding.publishButton.isEnabled = false
            viewModel.createArticle().collectIn(lifecycleScope) {
                binding.publishButton.isEnabled = true
                when (it) {
                    is CreateArticleResult.Success -> findNavController().popBackStack()

                    is CreateArticleResult.Error -> Snackbar.make(
                        binding.root,
                        it.t.localizedMessage ?: it.t.message
                        ?: getString(R.string.something_went_wrong),
                        Snackbar.LENGTH_LONG
                    ).top().show()
                }
            }
        }

        binding.chooseImage.setOnClickListener {

        }
    }
}
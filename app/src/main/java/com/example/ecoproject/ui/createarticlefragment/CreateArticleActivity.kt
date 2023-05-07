package com.example.ecoproject.ui.createarticlefragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseActivity
import com.example.ecoproject.databinding.CreateArticleFragmentBinding
import com.example.ecoproject.domain.usecases.article.CreateArticleResult
import com.example.ecoproject.domain.usecases.images.ImageUploadResult
import com.example.ecoproject.ui.di.DaggerMainActivityComponent
import com.example.ecoproject.ui.di.MainActivityComponent
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.onTextChanged
import com.example.ecoproject.ui.utils.UIUtils.top
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class CreateArticleActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: CreateArticleViewModel
    private lateinit var binding: CreateArticleFragmentBinding

    private val activityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.builder().dataComponent(component).build()
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                openUri(it)
            }
        }
    }

    private fun openUri(uri: Uri) {
        viewModel.uploadFile(contentResolver.openInputStream(uri))
            .collectIn(lifecycleScope) {
                if (isDestroyed) return@collectIn
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        activityComponent.createArticleSubcomponent.inject(this)
        binding = CreateArticleFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                    is CreateArticleResult.Success -> finish()

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
            activityResultLauncher.launch(Intent.createChooser(Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }, getString(R.string.choose_image)))
        }
    }
}
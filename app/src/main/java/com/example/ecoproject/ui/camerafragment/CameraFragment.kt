package com.example.ecoproject.ui.camerafragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.CameraFragmentBinding
import com.example.ecoproject.domain.usecases.analyse.AnalysesImageResult
import com.example.ecoproject.domain.usecases.images.GetImageBitmapResult
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.top
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.util.Calendar
import javax.inject.Inject

class CameraFragment : BaseFragment<MainActivity>() {
    private var imageUri: Uri? = null
    private val imageCaptureRequest =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it && imageUri != null) viewModel.getImage(
                requireContext().contentResolver.openInputStream(
                    imageUri!!
                )
            ).collectIn(lifecycleScope) { result ->
                when (result) {
                    is GetImageBitmapResult.Failed ->
                        Snackbar.make(
                            binding.root,
                            result.t.localizedMessage ?: result.t.message
                            ?: getString(R.string.something_went_wrong),
                            Snackbar.LENGTH_SHORT
                        ).top().show()

                    else -> Unit
                }
            }
            else AlertDialog.Builder(requireContext())
                .setTitle(R.string.image_not_taken)
                .setMessage(R.string.take_photo_to_use_this_feature)
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }

    @Inject
    lateinit var viewModel: CameraViewModel
    private lateinit var binding: CameraFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CameraFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bitmap.collectOnLifeCycle(this) {
            binding.noPhoto.isVisible = it == null
            binding.photoPreview.isVisible = it != null
            binding.photoPreview.setImageBitmap(it)
        }

        viewModel.canAnalyse.collectOnLifeCycle(this) {
            binding.analyseBtn.isEnabled = it
        }

        binding.takePhotoBtn.setOnClickListener {
            takePicture()
        }

        binding.analyseBtn.setOnClickListener {
            showProgress()
            viewModel.analyse().collectIn(lifecycleScope) {
                hideProgress()
                when (it) {
                    is AnalysesImageResult.Failed ->
                        Snackbar.make(
                            binding.root,
                            it.t.localizedMessage ?: it.t.message
                            ?: getString(R.string.something_went_wrong),
                            Snackbar.LENGTH_LONG
                        ).top().show()

                    is AnalysesImageResult.Success -> AlertDialog.Builder(requireContext())
                        .setTitle(R.string.analyse_response_title)
                        .setMessage(getString(R.string.analyse_reponse_message, it.analyse.type))
                        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        }
    }

    private fun takePicture() {
        val dir = File(requireContext().filesDir, "images").apply {
            if (!exists()) mkdirs()
        }

        val file = File(dir, "${Calendar.getInstance().timeInMillis}.png")
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            "${baseActivity.applicationContext.packageName}.fileprovider",
            file
        )
        imageCaptureRequest.launch(imageUri)
    }
}
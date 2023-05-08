package com.example.ecoproject.ui.auth.verificationfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.VerificationFragmentBinding
import com.example.ecoproject.domain.usecases.auth.VerifyCodeResult
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.onTextChanged
import com.example.ecoproject.ui.utils.UIUtils.replaceText
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class VerificationFragment : BaseFragment<MainActivity>() {

    @Inject
    lateinit var viewModel: VerificationViewModel

    private lateinit var binding: VerificationFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.verificationSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VerificationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.code.collectOnLifeCycle(this) {
            binding.textInput.replaceText(it)
        }

        binding.textInput.onTextChanged()
            .collectOnLifeCycle(this, Lifecycle.State.CREATED, viewModel.code)

        viewModel.isCode.collectOnLifeCycle(this) {
            binding.verifyButton.isEnabled = it
        }

        binding.verifyButton.setOnClickListener {
            showProgress()
            viewModel.validateCode().collectIn(lifecycleScope) {
                if (it is VerifyCodeResult.Error) {
                    it.t.printStackTrace()
                    hideProgress()
                    Snackbar.make(
                        binding.root,
                        it.t.localizedMessage ?: it.t.message
                        ?: getString(R.string.something_went_wrong),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
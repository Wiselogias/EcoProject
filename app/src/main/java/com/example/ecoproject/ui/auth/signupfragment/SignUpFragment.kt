package com.example.ecoproject.ui.auth.signupfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.ecoproject.common.mvvm.BaseFragment
import com.example.ecoproject.databinding.SignUpFragmentBinding
import com.example.ecoproject.domain.repositories.VerificationState
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.onTextChanged
import com.example.ecoproject.ui.utils.UIUtils.replaceText
import javax.inject.Inject

class SignUpFragment : BaseFragment<MainActivity>() {
    @Inject
    lateinit var viewModel: SignUpViewModel

    private lateinit var binding: SignUpFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity.activityComponent.signUpSubcomponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.phoneNumber.collectOnLifeCycle(this) {
            binding.phoneInput.replaceText(it)
        }
        binding.phoneInput.onTextChanged().collectOnLifeCycle(
            this,
            Lifecycle.State.CREATED,
            viewModel.phoneNumber
        )
        viewModel.isPhoneCorrect.collectOnLifeCycle(this) {
            binding.verifyButton.isEnabled = it
        }

        binding.verifyButton.setOnClickListener {
            viewModel.sendSMS()
        }

        viewModel.verificationState.collectOnLifeCycle(this) {
            if (it is VerificationState.Sent) {
                TODO("GO TO VERIFICATION FRAGMENT")
            }
        }
    }
}
package com.example.ecoproject.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseActivity
import com.example.ecoproject.databinding.ActivityMainBinding
import com.example.ecoproject.domain.repositories.AuthState
import com.example.ecoproject.ui.di.DaggerMainActivityComponent
import com.example.ecoproject.ui.di.MainActivityComponent
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import com.example.ecoproject.ui.utils.UIUtils.destinationFlow
import com.example.ecoproject.ui.utils.UIUtils.top
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    val activityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.builder().dataComponent(component).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityComponent.inject(this)
        binding.navBar.selectedItemId = R.id.action_map
    }

    override fun onResume() {
        super.onResume()

        val navigation = binding.navHostFragment.findNavController().also {
            binding.navBar.setupWithNavController(it)
        }

        navigation.destinationFlow().collectOnLifeCycle(this) {
            when (it.id) {
                R.id.action_map, R.id.action_articles, R.id.action_camera, R.id.action_profile, R.id.pointFragment ->
                    binding.navBar.isVisible = true

                else -> binding.navBar.isVisible = false
            }
        }

        viewModel.authState.collectOnLifeCycle(this) {
            when (it) {
                is AuthState.NotAuthed -> navigation.navigate(
                    R.id.signUpFragment, null, NavOptions.Builder().setLaunchSingleTop(true).build()
                )

                is AuthState.Authed -> navigation.navigate(
                    R.id.action_articles,
                    null,
                    NavOptions.Builder().setLaunchSingleTop(true).build()
                )

                is AuthState.Error -> {
                    Snackbar.make(
                        binding.root,
                        it.t.localizedMessage ?: it.t.message
                        ?: getString(R.string.something_went_wrong),
                        Snackbar.LENGTH_LONG
                    ).top().show()
                }
            }
        }
    }
}

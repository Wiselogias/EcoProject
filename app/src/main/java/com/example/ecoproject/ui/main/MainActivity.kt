package com.example.ecoproject.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ecoproject.R
import com.example.ecoproject.common.mvvm.BaseActivity
import com.example.ecoproject.databinding.ActivityMainBinding
import com.example.ecoproject.domain.repositories.AuthState
import com.example.ecoproject.ui.di.DaggerMainActivityComponent
import com.example.ecoproject.ui.di.MainActivityComponent
import com.example.ecoproject.ui.utils.UIUtils.collectOnLifeCycle
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    val activityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.builder().dataComponent(component).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityComponent.inject(this)
        val navigation = Navigation.findNavController(this, R.id.nav_host_fragment).also {
            binding.navBar.setupWithNavController(it)
        }
        viewModel.authState.collectOnLifeCycle(this) {
            when (it) {
                is AuthState.NotAuthed -> {
                    navigation.navigate(
                        R.id.signUpFragment,
                        null,
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                    binding.navBar.isVisible = false
                }

                is AuthState.Authed -> {
                    navigation.navigate(R.id.action_articles)
                    binding.navBar.isVisible = true
                }

                else -> Unit
            }
        }
    }
}

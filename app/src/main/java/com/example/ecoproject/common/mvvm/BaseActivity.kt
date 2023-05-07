package com.example.ecoproject.common.mvvm

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ecoproject.R
import com.example.ecoproject.app.App
import com.example.ecoproject.data.di.DataComponent

open class BaseActivity : AppCompatActivity() {

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        canBack = true
        filePickerListener(uri)
        filePickerListener = { }
    }


    private var filePickerListener: (Uri?) -> Unit = { }
    fun chooseImage(listener: (Uri?) -> Unit) {
        filePickerListener = listener
        canBack = false
        filePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private var canBack: Boolean = true


    val app: App by lazy {
        application as App
    }

    val component: DataComponent by lazy {
        app.dataComponent
    }

    private val appPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }
    override fun onResume() {
        super.onResume()

        checkPermissions()
    }
    private fun checkPermissions() {
        appPermissions.map {
            val isGranted = ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
            it to isGranted
        }.filterNot { it.second }.map { it.first }.let { permissions ->
            println("PSG $permissions")
            if (permissions.isNotEmpty()) AlertDialog.Builder(this)
                .setTitle(R.string.permissionsAlertTitle)
                .setMessage(R.string.permissionsText)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    requestPermissions(permissions)
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    finish()
                }
                .create().show()
        }
    }

    private fun requestPermissions(permissions: List<String>) {
        requestPermissionLauncher.launch(permissions.toTypedArray())
    }


    override fun onBackPressed() {
        if (canBack) super.onBackPressed()
    }

}
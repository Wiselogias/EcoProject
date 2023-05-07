package com.example.ecoproject.ui.progress

import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding
import com.example.ecoproject.R

class ProgressDialog(context: Context) : AppCompatDialog(context, R.style.ProgressDialog) {

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progress = ProgressBar(context)
        progress.setPadding(context.resources.getDimensionPixelSize(R.dimen.progress_dialog_padding))
        progress.background = AppCompatResources.getDrawable(context, R.drawable.progress_dialog_background)
        setContentView(progress)
    }
}
package com.example.ecoproject.ui.camerafragment

import android.graphics.Bitmap
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.analyse.AnalyseImageParams
import com.example.ecoproject.domain.usecases.analyse.AnalyseImageUseCase
import com.example.ecoproject.domain.usecases.images.GetImageBitmapResult
import com.example.ecoproject.domain.usecases.images.GetImageBitmapUseCase
import com.example.ecoproject.ui.di.ActivityScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import java.io.InputStream
import javax.inject.Inject

@OptIn(FlowPreview::class)
@ActivityScope
class CameraViewModel @Inject constructor(
    private val getImageBitmapUseCase: GetImageBitmapUseCase,
    private val analyseImageUseCase: AnalyseImageUseCase,
) : BaseViewModel() {

    val bitmap = MutableStateFlow<Bitmap?>(null)

    val canAnalyse = bitmap.map { it != null }.shareWhileSubscribed()

    fun getImage(inputStream: InputStream?) = getImageBitmapUseCase(inputStream).onEach {
        if (it is GetImageBitmapResult.Success) bitmap.emit(it.bitmap)
    }

    fun analyse() = bitmap.take(1).filterNotNull().flatMapConcat {
        analyseImageUseCase(AnalyseImageParams(it, 0.0, 0.0))
    }

}
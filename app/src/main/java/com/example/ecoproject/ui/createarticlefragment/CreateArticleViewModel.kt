package com.example.ecoproject.ui.createarticlefragment

import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.article.CreateArticleParams
import com.example.ecoproject.domain.usecases.article.CreateArticleUseCase
import com.example.ecoproject.domain.usecases.images.ImageUploadResult
import com.example.ecoproject.domain.usecases.images.UploadImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import java.io.InputStream
import javax.inject.Inject

class CreateArticleViewModel @Inject constructor(
    private val createArticleUseCase: CreateArticleUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
) : BaseViewModel() {
    val title = MutableStateFlow("")
    val text = MutableStateFlow("")

    private val imageRef = MutableStateFlow("")

    val canCreateArticle = combine(
        title,
        text,
        imageRef
    ) { title, text, imageRef ->
        title.isNotEmpty() && text.isNotEmpty() && imageRef.isNotEmpty()
    }.shareWhileSubscribed()

    fun createArticle() = combine(
        title.take(1),
        text.take(1),
        imageRef.take(1)
    ) { title, text, imageRef ->
        CreateArticleParams(title, text, imageRef)
    }.flatMapConcat {
        createArticleUseCase(it)
    }

    fun uploadFile(inputStream: InputStream?) = uploadImageUseCase(inputStream).onEach {
        if (it is ImageUploadResult.Success) imageRef.emit(it.ref)
    }
}
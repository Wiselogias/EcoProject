package com.example.ecoproject.ui.createarticlefragment

import com.example.ecoproject.common.mvvm.FragmentComponent
import dagger.Subcomponent

@Subcomponent
interface CreateArticleSubcomponent : FragmentComponent<CreateArticleFragment> {
    fun inject(activity: CreateArticleActivity)
}
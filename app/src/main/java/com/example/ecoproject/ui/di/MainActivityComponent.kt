package com.example.ecoproject.ui.di

import com.example.ecoproject.data.di.DataComponent
import com.example.ecoproject.ui.articlesfragment.ArticlesSubcomponent
import com.example.ecoproject.ui.auth.signupfragment.SignUpSubcomponent
import com.example.ecoproject.ui.auth.verificationfragment.VerificationSubcomponent
import com.example.ecoproject.ui.camerafragment.CameraSubcomponent
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.mapfragment.MapSubcomponent
import com.example.ecoproject.ui.profilefragment.ProfileSubcomponent
import dagger.Component

@ActivityScope
@Component(
    dependencies = [
        DataComponent::class
    ]
)
interface MainActivityComponent {
    fun mapSubcomponent(): MapSubcomponent
    fun signUpSubcomponent(): SignUpSubcomponent
    fun verificationSubcomponent(): VerificationSubcomponent
    fun profileSubcomponent(): ProfileSubcomponent
    fun cameraSubcomponent(): CameraSubcomponent
    fun articlesSubcomponent(): ArticlesSubcomponent
    fun inject(mainActivity: MainActivity)
}
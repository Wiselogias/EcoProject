package com.example.ecoproject.ui.di

import com.example.ecoproject.data.di.DataComponent
import com.example.ecoproject.ui.article.ArticleSubcomponent
import com.example.ecoproject.ui.articlesfragment.ArticlesFragment
import com.example.ecoproject.ui.auth.signupfragment.SignUpSubcomponent
import com.example.ecoproject.ui.auth.verificationfragment.VerificationSubcomponent
import com.example.ecoproject.ui.camerafragment.CameraFragment
import com.example.ecoproject.ui.createarticlefragment.CreateArticleSubcomponent
import com.example.ecoproject.ui.favoritearticlesfragment.FavoriteArticlesSubcomponent
import com.example.ecoproject.ui.favoritepointsfragment.FavoritePointsSubcomponent
import com.example.ecoproject.ui.main.MainActivity
import com.example.ecoproject.ui.mapfragment.MapFragment
import com.example.ecoproject.ui.point.PointSubcomponent
import com.example.ecoproject.ui.profilefragment.ProfileFragment
import dagger.Component

@ActivityScope
@Component(
    dependencies = [
        DataComponent::class
    ]
)
interface MainActivityComponent {
    val signUpSubcomponent: SignUpSubcomponent
    val verificationSubcomponent: VerificationSubcomponent
    val createArticleSubcomponent: CreateArticleSubcomponent
    val favoriteArticlesSubcomponent: FavoriteArticlesSubcomponent
    val articleSubcomponent: ArticleSubcomponent
    val pointSubcomponent: PointSubcomponent
    val favoritePointsSubcomponent: FavoritePointsSubcomponent
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: MapFragment)
    fun inject(fragment: ArticlesFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: CameraFragment)

}
package com.example.ecoproject.domain.entities

import org.joda.time.DateTime

data class ArticleEntity(
    val id: String,
    val title: String,
    val text: String,
    val imageReference: String,
    val time: DateTime
)
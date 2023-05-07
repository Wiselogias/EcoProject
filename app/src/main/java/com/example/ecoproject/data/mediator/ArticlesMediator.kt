package com.example.ecoproject.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.ecoproject.data.db.dao.ArticleDao
import com.example.ecoproject.data.db.database.AppDataBase
import com.example.ecoproject.data.db.entities.ArticleRoomEntity
import com.example.ecoproject.data.firebase.datasource.ArticleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticlesMediator @Inject constructor(
    private val articleDataSource: ArticleDataSource,
    private val articleDao: ArticleDao,
    private val appDataBase: AppDataBase
) : RemoteMediator<Int, ArticleRoomEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleRoomEntity>
    ): MediatorResult {
        try {
            if (loadType == LoadType.APPEND)
                return MediatorResult.Success(endOfPaginationReached = true)

            val local = withContext(Dispatchers.IO) {
                articleDao.getArticlesPaged(state.config.pageSize, 0)
            }
            updateLocalDB(local.firstOrNull()?.id ?: "", state.config.pageSize)

            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun updateLocalDB(stopId: String, limit: Int) = withContext(Dispatchers.IO) {
        println("Updating articles")
        appDataBase.withTransaction {
            var remote = articleDataSource.getArticlesPaged(limit, null)
            while (remote.isNotEmpty()) {
                remote.forEach {
                    if (stopId.isNotEmpty() && it.id == stopId) return@withTransaction
                    articleDao.upsert(
                        ArticleRoomEntity(
                            it.id,
                            it.title ?: "No title",
                            it.text ?: "No text",
                            it.imageReference ?: "",
                            DateTime(it.time),
                            it.author ?: "No author"
                        )
                    )
                }
                remote = articleDataSource.getArticlesPaged(
                    limit,
                    remote.last().documentSnapshot
                )
            }
        }
    }
}
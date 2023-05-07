package com.example.ecoproject.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ecoproject.data.db.dao.ArticleDao
import com.example.ecoproject.data.db.entities.ArticleRoomEntity
import com.example.ecoproject.data.db.mappers.ArticleMapper
import com.example.ecoproject.data.firebase.datasource.ArticleDataSource
import com.example.ecoproject.domain.entities.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import javax.inject.Inject

class ArticlePagingSource @Inject constructor(
    private val articleDataSource: ArticleDataSource,
    private val articleDao: ArticleDao
) : PagingSource<Int, ArticleEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


    private suspend fun updateLocalDB(stopId: String, limit: Int) = withContext(Dispatchers.IO) {
        var remote = articleDataSource.getArticlesPaged(limit, null)
        while (remote.isNotEmpty()) {
            remote.forEach {
                if (stopId.isNotEmpty() && it.id == stopId) return@withContext
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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        try {
            val page = params.key ?: 1
            if (page == 1) {
                val local = withContext(Dispatchers.IO) {
                    articleDao.getArticlesPaged(params.loadSize, 0)
                }
                updateLocalDB(local.firstOrNull()?.id ?: "", params.loadSize)
            }
            val result = withContext(Dispatchers.IO) {
                articleDao.getArticlesPaged(params.loadSize, params.loadSize * (page - 1))
            }

            return LoadResult.Page(
                data = result.map { ArticleMapper.fromRoomEntityToDomainEntity(it) },
                nextKey = if (result.size < params.loadSize) null else page + 1,
                prevKey = if (page >= 1) page - 1 else null
            )
        } catch (t: Throwable) {
            t.printStackTrace()
            Log.println(Log.INFO, "PSG", "Error: ${t.message}")

            return LoadResult.Error(t)
        }
    }
}
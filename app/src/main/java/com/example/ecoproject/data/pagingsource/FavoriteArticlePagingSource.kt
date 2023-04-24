package com.example.ecoproject.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ecoproject.data.db.dao.FavoriteArticleDao
import com.example.ecoproject.data.db.mappers.FavoriteArticleMapper
import com.example.ecoproject.domain.entities.ArticleEntity
import javax.inject.Inject

class FavoriteArticlePagingSource @Inject constructor(
    private val favoriteArticleDao: FavoriteArticleDao
) : PagingSource<Int, ArticleEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        try {
            val page = params.key ?: 1
            val result =
                favoriteArticleDao.getArticlesPaged(params.loadSize, params.loadSize * (page - 1))
            return LoadResult.Page(
                data = result.map { FavoriteArticleMapper.fromRoomEntityToDomainEntity(it) },
                nextKey = if (result.size < params.loadSize) null else page + 1,
                prevKey = if (page >= 1) page - 1 else null
            )
        } catch (t: Throwable) {
            return LoadResult.Error(t)
        }
    }

}
package com.example.ecoproject.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ecoproject.data.db.dao.FavoritePointDao
import com.example.ecoproject.data.db.mappers.FavoritePointMapper
import com.example.ecoproject.domain.entities.PointEntity
import javax.inject.Inject

class FavoritePointPagingSource @Inject constructor(
    private val favoritePointDao: FavoritePointDao
) : PagingSource<Int, PointEntity>() {
    override fun getRefreshKey(state: PagingState<Int, PointEntity>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PointEntity> {
        try {
            val page = params.key ?: 1
            val result =
                favoritePointDao.getPointsPaged(params.loadSize, params.loadSize * (page - 1))
            return LoadResult.Page(
                data = result.map { FavoritePointMapper.fromRoomEntityToDomainEntity(it) },
                nextKey = if (result.size < params.loadSize) null else page + 1,
                prevKey = if (page >= 1) page - 1 else null
            )
        } catch (t: Throwable) {
            return LoadResult.Error(t)
        }
    }

}
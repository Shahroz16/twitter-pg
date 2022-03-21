package com.shahroz.twitterpg.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahroz.twitterpg.data.repositories.TwitterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import twitter4j.Status
import javax.inject.Inject

class TimeLineDateSource @Inject constructor(private val twitterRepository: TwitterRepository) :
    PagingSource<Int, Status>() {

    override fun getRefreshKey(state: PagingState<Int, Status>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Status> {
        return try {
            withContext(Dispatchers.IO) {
                val nextPage = params.key ?: 1
                val tweets = twitterRepository.getTimeLine(page = nextPage)
                LoadResult.Page(
                    data = tweets,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (tweets.isEmpty()) null else nextPage + 1
                )
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}

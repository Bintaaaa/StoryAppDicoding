package com.bintaaaa.storyappdicoding.data.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem

class StoryPagingSource(private val storyService: StoryService): PagingSource<Int, StoryItem>() {
    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, StoryItem>): Int? {
       return  state.anchorPosition?.let { anchorPosition->
           val anchorPage = state.closestPageToPosition(anchorPosition)
           anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)

       }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryItem> {
       return  try {
            val position = params.key ?: INITIAL_PAGE_INDEX
           val responseData = storyService.stories(page = position, size = params.loadSize)
           val storyList = responseData.listStory ?: emptyList()
           LoadResult.Page(
               data = storyList,
               prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
               nextKey = if (storyList.isEmpty()) null else position + 1
           )
       }catch (exception: Exception){
           return LoadResult.Error(exception)
       }
    }
}
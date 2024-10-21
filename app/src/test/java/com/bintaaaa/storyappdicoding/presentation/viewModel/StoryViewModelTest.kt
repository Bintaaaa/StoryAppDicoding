package com.bintaaaa.storyappdicoding.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem
import com.bintaaaa.storyappdicoding.presentation.ui.StoryAdapter
import com.bintaaaa.storyappdicoding.repository.StoryRepository
import com.bintaaaa.storyappdicoding.utils.DataDummy
import com.bintaaaa.storyappdicoding.utils.createPagingDataLiveData
import com.bintaaaa.storyappdicoding.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.mockito.Mockito.`when`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mainDispatcherRules = UnconfinedTestDispatcher()



    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var storyViewModel: StoryViewModel
    private val dummyData = DataDummy.generateDummyStoryData()

    @Before
    fun setUp(){
        Dispatchers.setMain(mainDispatcherRules)
        storyViewModel = StoryViewModel(storyRepository)
    }

    @After
    fun finished() {
        Dispatchers.resetMain() // Mengembalikan Dispatcher ke keadaan semula
    }

    @Test
    fun `When Get Stories Should Not Null and Return Success`(){
        val observer = Observer<PagingData<StoryItem>>{}
        try{
            val expectedStory = MutableLiveData<PagingData<StoryItem>>()
            expectedStory.value = PagingData.from(dummyData)

            `when` (storyRepository.getStories()).thenReturn(expectedStory)


            val actualStory = storyViewModel.stories().observeForever(observer)

            Mockito.verify(storyRepository).getStories()
            Assert.assertNotNull(actualStory)
        }finally {
            storyViewModel.stories().removeObserver(observer)
        }
    }


    @Test
    fun `when getStories Should Return 5 Data and Return Success`() = runTest {
        val expectedStories = createPagingDataLiveData(dummyData)

        `when`(storyRepository.getStories()).thenReturn(expectedStories)

        val actualStories: PagingData<StoryItem> = storyViewModel.stories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualStories)

        Assert.assertEquals(dummyData.size, differ.snapshot().size)
    }

    @Test
    fun `when getStories Should Return Equals Data and Return Success`() = runTest {
        val expectedStories = createPagingDataLiveData(dummyData)

        `when`(storyRepository.getStories()).thenReturn(expectedStories)

        val actualStories: PagingData<StoryItem> = storyViewModel.stories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualStories)

        Assert.assertEquals(dummyData[0].description, differ.snapshot()[0]?.description)
    }

    @Test
    fun `when getStories is Empty Should Return Zero Data and Return Success`() = runTest {
        val expectedStories = createPagingDataLiveData(emptyList<StoryItem>())

        `when`(storyRepository.getStories()).thenReturn(expectedStories)

        val actualStories: PagingData<StoryItem> = storyViewModel.stories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualStories)

        Assert.assertEquals(0, differ.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
package com.assignment.kotlinmvvm

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assignment.kotlinmvvm.data.OperationCallback
import com.assignment.kotlinmvvm.model.Assignment
import com.assignment.kotlinmvvm.model.AssignmentDataSource
import com.assignment.kotlinmvvm.viewmodel.MuseumViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.*
import org.mockito.Mockito.*

class MVVMUnitTest {

    @Mock
    private lateinit var repository: AssignmentDataSource
    @Mock private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<Assignment>>

    private lateinit var viewModel:MuseumViewModel

    private lateinit var isViewLoadingObserver:Observer<Boolean>
    private lateinit var onMessageErrorObserver:Observer<Any>
    private lateinit var emptyListObserver:Observer<Boolean>
    private lateinit var onRenderMuseumsObserver:Observer<List<Assignment>>

    private lateinit var assignmentEmptyList:List<Assignment>
    private lateinit var assignmentList:List<Assignment>

    /**
     //https://jeroenmols.com/blog/2019/01/17/livedatajunit5/
     //Method getMainLooper in android.os.Looper not mocked

     java.lang.IllegalStateException: operationCallbackCaptor.capture() must not be null
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)

        viewModel= MuseumViewModel(repository)

        mockData()
        setupObservers()
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns empty data`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            //onMessageError.observeForever(onMessageErrorObserver)
            isEmptyList.observeForever(emptyListObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        verify(repository, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(assignmentEmptyList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        //Assert.assertNotNull(viewModel.onMessageError.value) //java.lang.AssertionError
        //Assert.assertNotNull(viewModel.isEmptyList.value)
        Assert.assertTrue(viewModel.isEmptyList.value==true)
        Assert.assertTrue(viewModel.museums.value?.size==0)
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns full data`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        verify(repository, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(assignmentList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.museums.value?.size==3)
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns an error`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(repository, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers(){
        isViewLoadingObserver= mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver= mock(Observer::class.java) as Observer<Any>
        emptyListObserver= mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver= mock(Observer::class.java)as Observer<List<Assignment>>
    }

    private fun mockData(){
        assignmentEmptyList= emptyList()
        val mockList:MutableList<Assignment>  = mutableListOf()
        mockList.add(Assignment("Beavers","http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg","Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony"))
        mockList.add(Assignment("Flag","http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png",""))
        mockList.add(Assignment("Transportation","http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg","It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal."))

        assignmentList= mockList.toList()
    }
}
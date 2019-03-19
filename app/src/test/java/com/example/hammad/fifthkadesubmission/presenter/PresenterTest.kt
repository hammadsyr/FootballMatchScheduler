package com.example.hammad.fifthkadesubmission.presenter

import com.example.hammad.fifthkadesubmission.matches.view.MatchCallback
import com.example.hammad.fifthkadesubmission.matches.view.MatchView
import com.example.hammad.fifthkadesubmission.util.APIResponse
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class PresenterTest {
    @Mock
    private lateinit var matchView: MatchView

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var response : APIResponse.Matches

    @Mock
    private lateinit var presenter: Presenter<MatchView>



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = Presenter(matchView, repository)
    }

    @Test
    fun MatchTest() {
        val id = "4328"
        presenter.getPrevMatch(id)

        argumentCaptor<MatchCallback<APIResponse.Matches>>().apply {
            verify(repository).getPrevMatch(eq(id), capture())
            firstValue.onListMatch(response)
        }

        Mockito.verify(matchView).onShowLoading()
        Mockito.verify(matchView).onListMatch(response)
    }
}





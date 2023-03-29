package com.edu.mf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.repository.model.community.CommentListItem
import kotlinx.coroutines.launch

class CommunityViewModel: ViewModel() {
    private val _boardList = MutableLiveData<List<BoardListItem>>()
    val boardList: LiveData<List<BoardListItem>> = _boardList

    private val _commentList = MutableLiveData<List<CommentListItem>>()
    val commentList: LiveData<List<CommentListItem>> = _commentList

    fun getBoardList(boardList: List<BoardListItem>){
        viewModelScope.launch {
            _boardList.value = boardList
        }
    }

    fun getCommentList(commentList: List<CommentListItem>){
        viewModelScope.launch {
            _commentList.value = commentList
        }
    }
}
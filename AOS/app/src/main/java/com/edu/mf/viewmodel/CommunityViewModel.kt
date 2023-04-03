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

    private val _boardListItem = MutableLiveData<BoardListItem?>()
    val boardListItem: LiveData<BoardListItem?> = _boardListItem

    private val _commentList = MutableLiveData<List<CommentListItem>>()
    val commentList: LiveData<List<CommentListItem>> = _commentList

    private val _commentItem = MutableLiveData<CommentListItem>()
    val commentItem: LiveData<CommentListItem> = _commentItem

    fun getBoardList(boardList: List<BoardListItem>){
        viewModelScope.launch {
            _boardList.value = boardList
        }
    }

    fun setBoardItem(boardListItem: BoardListItem?){
        viewModelScope.launch {
            _boardListItem.value = boardListItem
        }
    }

    fun getCommentList(commentList: List<CommentListItem>){
        viewModelScope.launch {
            _commentList.value = commentList
        }
    }

    fun getCommentItem(commentItem: CommentListItem){
        viewModelScope.launch {
            _commentItem.value = commentItem
        }
    }
}
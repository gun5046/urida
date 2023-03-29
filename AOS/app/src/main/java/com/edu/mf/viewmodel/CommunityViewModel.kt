package com.edu.mf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.mf.repository.model.community.BoardListItem
import kotlinx.coroutines.launch

class CommunityViewModel: ViewModel() {
    private val _boardListItem = MutableLiveData<List<BoardListItem>>()
    val boardListItem: LiveData<List<BoardListItem>> = _boardListItem

    fun getBoardList(boardListItem: List<BoardListItem>){
        viewModelScope.launch {
            _boardListItem.value = boardListItem
        }
    }
}
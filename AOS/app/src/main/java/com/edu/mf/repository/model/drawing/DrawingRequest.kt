package com.edu.mf.repository.model.drawing

data class DrawingRequest(
    val drawing : ArrayList<ArrayList<ArrayList<Int>>>,
    val canvasWidth: Int,
    val canvasHeight: Int
)

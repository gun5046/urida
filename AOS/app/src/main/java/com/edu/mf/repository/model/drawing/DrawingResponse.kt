package com.edu.mf.repository.model.drawing

data class DrawingResponse(
    val predictionType: Int,
    val firstPrediction: String,
    val secondPrediction: String
)

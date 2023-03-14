package com.edu.mf.repository.api

import com.edu.mf.repository.model.drawing.DrawingResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface DrawingService {
    @POST("ai/prediction/")
    fun drawingResult(@Query("drawing") drawing : ArrayList<ArrayList<ArrayList<Int>>>): Call<DrawingResponse>
}
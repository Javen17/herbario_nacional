package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.funghi.ShapeType
import retrofit2.http.GET

interface FormTypeInterface {
    @GET("api/mushroom_form_type/")
    suspend fun requestFormType(): List<ShapeType>
}
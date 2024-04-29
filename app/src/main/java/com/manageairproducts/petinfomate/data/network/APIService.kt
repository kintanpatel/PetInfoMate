package com.manageairproducts.petinfomate.data.network

import com.manageairproducts.petinfomate.model.Animal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("animals")
    suspend fun getPetInfo(
        @Query("name") name: String = ""
    ): Response<List<Animal>>
}
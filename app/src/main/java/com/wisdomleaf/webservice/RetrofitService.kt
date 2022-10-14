package com.wisdomleaf.webservice

import com.wisdomleaf.mvvm.model.response.GetProductListResponse
import com.wisdomleaf.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @GET(Constants.ApiMethods.GET_LIST)
    suspend fun getRecommendProduct(
        @Query("page") pageNumber: Int?,
        @Query("limit") pageSize: Int?,
    ): Response<ArrayList<GetProductListResponse>>

}
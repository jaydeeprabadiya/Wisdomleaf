package com.wisdomleaf.webservice

import com.wisdomleaf.mvvm.model.response.GetProductListResponse
import com.wisdomleaf.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    /*@GET(Constants.ApiMethods.GET_ROCKET)
    suspend fun getRocket(): Response<ArrayList<GetRocketResponse>>*/

    @GET(Constants.ApiMethods.GET_ROCKET_DETAILS)
    suspend fun getRecommendProduct(
        @Query("page") pageNumber: Int?,
        @Query("limit") pageSize: Int?,
    ): Response<ArrayList<GetProductListResponse>>

   /* @GET(Constants.ApiMethods.GET_ROCKET_DETAILS + "/{rocketid}")
    suspend fun getRocketdetails(
        @Path("rocketid") phone: String?
    ): Response<GetRocketDetailsResponse>


    @POST(Constants.ApiMethods.GET_CHECK_STATUS)
    suspend fun checkstatus(
        @Query("phone") phone: String?,
    ): Response<GetNewsResponse>*/

}
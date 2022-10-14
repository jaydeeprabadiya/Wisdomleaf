package com.wisdomleaf.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wisdomleaf.collection.ListProductRequest
import com.wisdomleaf.mvvm.model.APIError
import com.wisdomleaf.mvvm.model.response.GetProductListResponse
import com.wisdomleaf.utils.Logger
import com.wisdomleaf.webservice.DataWrapper
import com.wisdomleaf.webservice.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

class ListViewModel (app: Application) : AndroidViewModel(app) {
    private var TAG = this::class.java.simpleName

    val service = RetrofitFactory.get()

    fun getListData(request: ListProductRequest?): LiveData<DataWrapper<ArrayList<GetProductListResponse>>> {
        val mDataWrapperlist = DataWrapper<ArrayList<GetProductListResponse>>()
        val mResponseData = MutableLiveData<DataWrapper<ArrayList<GetProductListResponse>>>()

        CoroutineScope(Dispatchers.IO).launch {
            val withCategoryResponse: Response<ArrayList<GetProductListResponse>>?

            try {
                withCategoryResponse = service.getRecommendProduct(
                    request?.pageNumber,
                    request?.pageSize,
                )

                withContext(Dispatchers.Main) {
                    try {
                        if (withCategoryResponse!!.isSuccessful && withCategoryResponse.code() == 200){
                            mDataWrapperlist.data = withCategoryResponse.body()
                            mResponseData.value = mDataWrapperlist
                        }else {
                            Logger.e(TAG, "Error :: " + withCategoryResponse.code())
                            mDataWrapperlist.apiError =
                                APIError(withCategoryResponse.code(), withCategoryResponse.message())
                            mResponseData.value = mDataWrapperlist
                        }
                    } catch (e: HttpException) {
                        Logger.e(TAG, "HttpException :: " + e.message)
                        mDataWrapperlist.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperlist
                    } catch (e: Throwable) {
                        Logger.e(TAG, "Throwable :: " + e.message)
                        mDataWrapperlist.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperlist
                    } catch (e: ConnectException) {
                        Logger.e(TAG, "ConnectException :: " + e.message)

                        mDataWrapperlist.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperlist
                    }
                }
            } catch (e: Throwable) {
                Logger.e(TAG, "Error! ${e.message}")
                mDataWrapperlist.apiError = APIError(100, e.message!!)

                withContext(Dispatchers.Main) {
                    mResponseData.value = mDataWrapperlist
                }
            }
        }

        return mResponseData
    }









}
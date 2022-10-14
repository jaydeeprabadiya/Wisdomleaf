package com.wisdomleaf.mvvm.view.ui

import android.app.Activity
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wisdomleaf.R
import com.wisdomleaf.base.BaseActivity
import com.wisdomleaf.collection.ListProductRequest
import com.wisdomleaf.componet.DefaultItemDecorator
import com.wisdomleaf.mvvm.model.APIError
import com.wisdomleaf.mvvm.model.response.GetProductListResponse
import com.wisdomleaf.mvvm.view.adapter.ListProductAdapter
import com.wisdomleaf.mvvm.viewmodel.ListViewModel
import com.wisdomleaf.utils.ConnectivityReceiver
import com.wisdomleaf.webservice.ApiObserver
import kotlinx.android.synthetic.main.activity_list.*
import java.util.ArrayList

class ListActivity : BaseActivity(), View.OnClickListener {
    private var TAG = this::class.java.simpleName
    private var viewrocketModel: ListViewModel? = null
    private var recommendedProductAdapter: ListProductAdapter? = null
    private var request: ListProductRequest? = null
    private var manager: LinearLayoutManager? = null
    private var pageNumber = 1
    private var pageSize = 20
    private var loading = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        viewrocketModel = ViewModelProvider(this).get(ListViewModel::class.java)
        //binding.rcrocket.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        manager = LinearLayoutManager(this@ListActivity)
        rcrocket?.layoutManager = manager

        rcrocket.addOnScrollListener(recyclerViewOnScrollListener)

        rcrocket.addItemDecoration(
            DefaultItemDecorator(
                0,
                60
            )
        )


        request = ListProductRequest(
            pageNumber,
            pageSize,
        )

        getList(request)




        rcrocket.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {

            var gestureDetector =
                GestureDetector(viewActivity(), object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapUp(e: MotionEvent): Boolean {
                        return true
                    }
                })

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    val position = rv.getChildAdapterPosition(child)



                }

                return null == true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })




    }

    

/*
    private fun getOffers() {

        when {
            isNetworkConnected(viewActivity()) -> {
                showProgress()
                viewrocketModel!!.getCategoryData()
                    .observe(
                        this,
                        ApiObserver(object :
                            ApiObserver.ChangeListener<ArrayList<GetRocketResponse>> {
                            override fun onSuccess(dataWrapper: ArrayList<GetRocketResponse>?) {
                                try {
                                    hideProgress()
                                    if (dataWrapper?.size!! > 0) {
                                        rocketAdapter = RocketlistAdapter(dataWrapper)
                                        rcrocket.adapter = rocketAdapter


                                    }
                                }
                                catch (e:Exception)
                                {
                                    e.printStackTrace()
                                }
                            }

                            override fun onError(error: APIError?) {
                                try {
                                    hideProgress()
                                    showToast(error?.httpErrorMessage!!)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        })
                    )
            }
            else -> {
                showToast(getString(R.string.no_internet_message))
            }
        }
    }
*/

    private fun getList(request: ListProductRequest?) {
        if (ConnectivityReceiver.isNetworkConnected(viewActivity())) {
            showProgress()

            viewrocketModel?.getListData(request)!!.observe(
                    this@ListActivity,
                    ApiObserver(object :
                        ApiObserver.ChangeListener<ArrayList<GetProductListResponse>> {
                        override fun onSuccess(dataWrapper: ArrayList<GetProductListResponse>?) {
                            try {
                                hideProgress()
                                if (dataWrapper?.size!! > 0) {
                                    if (pageNumber == 1) {
                                        recommendedProductAdapter = ListProductAdapter(dataWrapper)
                                        rcrocket.adapter = recommendedProductAdapter
                                    }
                                    else {
                                        recommendedProductAdapter?.addData(dataWrapper)
                                        recommendedProductAdapter?.notifyDataSetChanged()
                                    }

                                    if (dataWrapper?.size!! > 0) {
                                        loading = true
                                    }



                                }
                            }
                            catch (e:Exception)
                            {
                                e.printStackTrace()
                            }
                        }

                        override fun onError(error: APIError?) {
                            try {
                                hideProgress()
                                showToast(error?.httpErrorMessage!!)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    })
                )

















           /* observe(
                this@ListActivity,
                ApiObserver<GetRecommendedProductResponse>(object :
                    ApiObserver.ChangeListener<GetRecommendedProductResponse> {
                    override fun onSuccess(response: GetRecommendedProductResponse?) {
                        hideProgress()
                        Log.e(TAG, "" + response);

                        if (response!=null) {

                            if (pageNumber == 1) {
                                recommendedProductAdapter =
                                    RecommendedProductAdapter(response)
                                rcylRecommended.adapter = recommendedProductAdapter

                                if (response?.result?.size!! > 0) {
                                    lblNoItems.visibility = View.GONE
                                } else {
                                    lblNoItems.visibility = View.VISIBLE
                                }
                            } else {
                                recommendedProductAdapter?.addData(response?.result)
                                recommendedProductAdapter?.notifyDataSetChanged()
                            }


                            if (response?.result?.size!! > 0) {
                                loading = true
                            }

                        }
                    }

                    override fun onError(error: APIError?) {
                        hideProgress()
                        showToast(error?.httpErrorMessage!!)
                    }
                })
            )
*/
        }
        else {
            showToast(viewActivity()?.getString(R.string.no_internet_message)!!)
        }
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount: Int = manager?.getChildCount()!!
                val totalItemCount: Int = manager?.getItemCount()!!
                val firstVisibleItemPosition: Int = manager?.findFirstVisibleItemPosition()!!
                if (loading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= MifareUltralight.PAGE_SIZE
                    ) {
                        loading = false
                        pageNumber++

                        request = ListProductRequest(
                            pageNumber,
                            pageSize,

                        )

                        getList(request)
                    }
                }
            }
        }


    override fun viewActivity(): Activity {
       return this
    }

    override fun onNetworkStateChange(isConnect: Boolean) {

    }

    override fun onClick(p0: View?) {

    }
}
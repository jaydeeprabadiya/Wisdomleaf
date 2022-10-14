package com.wisdomleaf.mvvm.view.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisdomleaf.R
import com.wisdomleaf.mvvm.model.response.GetProductListResponse
import kotlinx.android.synthetic.main.raw_recommended_product_iteam.view.*

class ListProductAdapter(internal var result: ArrayList<GetProductListResponse>?) :
    RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.raw_recommended_product_iteam, parent, false)
        context = parent?.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result?.size!!
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = result?.get(position)

        Glide.with(context!!).load(items?.download_url).placeholder(R.drawable.ic_thumnail)
            .into(holder.imgBanner)
        holder.imgBanner.clipToOutline = true
        holder.txtTitle.text = items?.author
        holder.txtDescription.text = items?.width.toString()
        holder.txtPrice.text = "$" + items?.height.toString()
    }

    fun getData(): ArrayList<GetProductListResponse>? {
        return result
    }

    fun addData(result: java.util.ArrayList<GetProductListResponse>?) {
        this.result?.addAll(result!!)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgBanner = v.imgBanner
        val txtTitle = v.txtTitle
        val txtDescription = v.txtDescription
        val txtPrice = v.txtPrice
    }
}
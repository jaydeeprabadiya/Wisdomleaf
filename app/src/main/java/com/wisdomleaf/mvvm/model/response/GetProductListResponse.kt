package com.wisdomleaf.mvvm.model.response

import java.io.Serializable


data class GetProductListResponse(
    internal val id: String? = null,
    internal val author: String? = null,
    internal val width: Int? = null,
    internal val height: Int? = null,
    internal val url: String? = null,
    internal val download_url: String? = null,
):Serializable




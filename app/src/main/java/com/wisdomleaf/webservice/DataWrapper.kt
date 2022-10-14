package com.wisdomleaf.webservice

import com.wisdomleaf.mvvm.model.APIError


class DataWrapper<T> {

    var apiError: APIError? = null
    var data: T? = null

}
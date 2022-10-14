package com.wisdomleaf.utils

interface Constants {

    /*Base URL*/
    companion object {
        val BASE_URL = "https://picsum.photos/v2/"

    }

    interface ApiHeaders {
        companion object {
            val API_TYPE_JSON = "application/json"
            val API_TYPE_TEXT = "text/plain"
            val AUTHORIZATION = "authorization"
            val AUTH_TOKEN = "Auth-Token"
        }
    }


    /*
   * API method
   * */
    interface ApiMethods {
        companion object {
            const val GET_LIST = "list?"
        }
    }
}
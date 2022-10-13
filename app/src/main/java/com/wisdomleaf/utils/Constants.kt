package com.wisdomleaf.utils

interface Constants {

    /*Base URL*/
    companion object {
        val BASE_URL = "https://api.spacexdata.com/v4/"

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

            const val GET_ROCKET = "rockets"
            const val GET_ROCKET_DETAILS = "rockets"
            const val GET_CHECK_STATUS = "http://ggicsangipur.in/loan/public/api/checkStatus"
            const val GET_INSTRUMENT_STATUS = "https://md.lmaxglobal.io/fixprof/instruments/depths"


        }
    }

    interface BUNDLE_KEY {
        companion object {
            const val ROKET_ID = "ROKET_ID"

        }

    }


}
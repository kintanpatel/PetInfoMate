package com.manageairproducts.petinfomate.base


sealed class Result<out T : Any> {
    class Success<out T : Any>(val data: T) : Result<T>()
    class Error(val exception: Exception, val message: String = exception.localizedMessage ?: "Issue in Message") :
        Result<Nothing>()

}



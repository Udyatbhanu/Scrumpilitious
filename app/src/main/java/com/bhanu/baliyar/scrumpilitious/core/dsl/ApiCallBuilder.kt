package com.bhanu.baliyar.scrumpilitious.core.dsl

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ApiCallBuilder<T> {
    var dispatcher: CoroutineDispatcher = Dispatchers.IO
    var apiCall: (suspend () -> Response<T>)? = null
    var onSuccess: (suspend (T) -> Unit)? = null
    var onError: (suspend (String) -> Unit)? = null
    var onLoading: (suspend () -> Unit)? = null
}

suspend fun <T> safeApiCallDsl(block: ApiCallBuilder<T>.() -> Unit): ResultWrapper<T> {
    val builder = ApiCallBuilder<T>().apply(block)
    val dispatcher = builder.dispatcher
    val apiCall = builder.apiCall ?: return ResultWrapper.Error("Missing apiCall")

    return withContext(dispatcher) {
        try {
            builder.onLoading?.invoke()
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    builder.onSuccess?.invoke(it)
                    ResultWrapper.Success(it)
                } ?: ResultWrapper.Error("Null body").also {
                    builder.onError?.invoke("Null body")
                }
            } else {
                val error = "API Error ${response.code()}: ${response.errorBody()?.string().orEmpty()}"
                builder.onError?.invoke(error)
                ResultWrapper.Error(error)
            }
        } catch (e: Exception) {
            val error = e.localizedMessage ?: "Unknown error"
            builder.onError?.invoke(error)
            ResultWrapper.Error(error)
        }
    }
}
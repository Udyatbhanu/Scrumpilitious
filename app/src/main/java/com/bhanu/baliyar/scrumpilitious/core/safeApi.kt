package com.bhanu.baliyar.scrumpilitious.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


fun <T> safeApiCall(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<T>
): Flow<ResultWrapper<T>> {
    return flow {
        emit(ResultWrapper.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultWrapper.Success(it))
                } ?: emit(ResultWrapper.Error("Response body was null"))
            } else {
                val errorMessage = response.errorBody()?.string().orEmpty()
                emit(ResultWrapper.Error("API Error: ${response.code()} $errorMessage"))
            }
        } catch (e: IOException) {
            emit(ResultWrapper.Error("Network error: ${e.localizedMessage}"))
        } catch (e: HttpException) {
            emit(ResultWrapper.Error("HTTP error: ${e.code()} ${e.message()}"))
        } catch (e: Exception) {
            emit(ResultWrapper.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }.flowOn(coroutineDispatcher)
}


//------------------------Vanilla  API call----------------------------------
//    override suspend fun getRecipes(): ResultWrapper<RecipesResponse> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = api.getRecipes()
//                if (response.isSuccessful) {
//                    val body = response.body()
//                    if (body != null) {
//                        ResultWrapper.Success(body)
//                    } else {
//                        ResultWrapper.Error()
//                    }
//                } else {
//                    ResultWrapper.Error()
//                }
//            } catch (e: Exception) {
//                ResultWrapper.Error(message = e.message, throwable = e)
//            }
//        }
//    }
//}


//-------------------------Non Nullable Safe API-----------------------------------
//override suspend fun getRecipes(): ResultWrapper<RecipesResponse> {
//    return safeApiCall(Dispatchers.IO) {
//        api.getRecipes()
//    }
//}
//
//suspend fun <T> safeApiCall(
//    dispatcher: CoroutineDispatcher,
//    apiCall: suspend () -> Response<T>
//): ResultWrapper<T> {
//    return withContext(dispatcher) {
//        try {
//            val response = apiCall.invoke()
//            if (response.isSuccessful) {
//                val body = response.body()
//                if (body != null) {
//                    ResultWrapper.Success(body)
//                } else {
//                    ResultWrapper.Error()
//                }
//            } else {
//                ResultWrapper.Error()
//            }
//        } catch (e: Exception) {
//            ResultWrapper.Error(message = e.message, throwable = e)
//        }
//    }
//}
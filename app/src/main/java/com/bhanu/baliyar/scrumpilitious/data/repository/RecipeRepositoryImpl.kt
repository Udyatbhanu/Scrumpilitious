package com.bhanu.baliyar.scrumpilitious.data.repository

import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.data.mdoels.RecipesResponse
import com.bhanu.baliyar.scrumpilitious.data.network.RecipeApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(private val api: RecipeApi) : RecipeRepository {


    override suspend fun getRecipes(): ResultWrapper<RecipesResponse> {

        return safeApiCall(coroutineDispatcher = Dispatchers.IO) {
            api.getRecipes()
        }


//        safeApiCall {
//
//            onApiCall : {}
//            onSuccess : a{}
//            onError:{}
//        }
    }

    suspend fun <T> safeApiCall(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): ResultWrapper<T> {
        return withContext(coroutineDispatcher) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        ResultWrapper.Success(body)
                    } else {
                        ResultWrapper.Error("The response body was null")
                    }
                } else {
                    ResultWrapper.Error("The was an error processing the response")
                }

            } catch (e: Exception) {
                ResultWrapper.Error("")
            }
        }
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
}


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
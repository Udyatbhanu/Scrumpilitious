package com.bhanu.baliyar.scrumpilitious.core

sealed class ResultWrapper<out T> {

    data class Success<out T>(val response : T) : ResultWrapper<T>()
    data class Error(val message : String? = null, val throwable: Throwable? = null) : ResultWrapper<Nothing>()
}
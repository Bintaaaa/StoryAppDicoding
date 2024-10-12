package com.bintaaaa.storyappdicoding.common.api

sealed class Result<out T> {
    data class  Error(val message: String) : Result<Nothing>()
    data class Success<out T>(val data: T?) : Result<T>()
    data object  Loading: Result<Nothing>()
}
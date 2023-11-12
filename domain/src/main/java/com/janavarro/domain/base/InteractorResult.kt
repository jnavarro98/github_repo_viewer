package com.janavarro.domain.base

class InteractorResult<T> constructor(var result: T? = null, var exception: Exception? = null) {

    fun isSuccessful() = exception == null
}
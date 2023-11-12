package com.janavarro.domain.base

import java.lang.Exception

class InteractorResult<T> constructor(var result: T? = null, var exception: Exception? = null) {

    fun isSuccessful() = exception == null
}
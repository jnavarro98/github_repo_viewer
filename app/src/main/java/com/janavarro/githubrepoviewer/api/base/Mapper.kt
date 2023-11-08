package com.janavarro.githubrepoviewer.api.base

interface Mapper<M, P> {

    fun map(model: M?): P?
}
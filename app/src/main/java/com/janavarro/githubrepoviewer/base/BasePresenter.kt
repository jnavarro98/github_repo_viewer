package com.janavarro.githubrepoviewer.base

abstract class BasePresenter<V : BaseView> {

    open var view: V? = null
    fun onDestroy() {
        view = null
    }
}
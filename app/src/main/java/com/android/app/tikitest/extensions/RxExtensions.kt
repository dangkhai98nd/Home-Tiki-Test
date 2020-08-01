package com.android.apps.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun Disposable.disposeIfNot() {
    takeIf { it.isDisposed.not() }?.dispose()
}

fun <T> Observable<T>.subscribeOnIO() = subscribeOn(Schedulers.io())

fun <T> Observable<T>.observeOnMain() = observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.observerOnIO() = observeOn(Schedulers.io())


fun <T> Flowable<T>.subscribeOnIO() = subscribeOn(Schedulers.io())

fun <T> Flowable<T>.observeOnMain() = observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.observerOnIO() = observeOn(Schedulers.io())

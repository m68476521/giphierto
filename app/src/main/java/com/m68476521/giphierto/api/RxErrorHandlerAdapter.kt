package com.m68476521.giphierto.api

import com.m68476521.giphierto.R
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.reflect.Type

class RxErrorHandlerAdapter : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        @Suppress("UNCHECKED_CAST")
        val adapter = retrofit.nextCallAdapter(this, returnType, annotations) as CallAdapter<R, Any>
        return RxCallAdapterWrapper(adapter)
    }

    private class RxCallAdapterWrapper<R>(
        private val wrapped: CallAdapter<R, Any>
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            return when (val result = wrapped.adapt(call)) {
                is Single<*> -> result.onErrorResumeNext { Single.error(asApiException(it)) }
                is Observable<*> -> result.onErrorResumeNext(
                    Function {
                        Observable.error(asApiException(it))
                    }
                )
                is Completable -> result.onErrorResumeNext {
                    Completable.error(asApiException(it))
                }
                else -> result
            }
        }

        private fun asApiException(throwable: Throwable): Throwable {
            return if (throwable is HttpException) ApiException(throwable) else throwable
        }
    }
}

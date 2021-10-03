package com.mayouf.chucknorristest.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody

fun OkHttpClient.Builder.createOkHttpBuilderExtension(body: String, code: Int) =
    this.addNetworkInterceptor { chain ->
        val proceed = chain.proceed(chain.request())

        return@addNetworkInterceptor proceed
            .newBuilder()
            .code(code)
            .body(body = body.toResponseBody("text/plain; charset=utf-8".toMediaTypeOrNull()))
            .build()
    }
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()

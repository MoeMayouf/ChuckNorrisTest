package com.mayouf.chucknorristest.domain.model

import com.google.gson.annotations.SerializedName

open class ChuckNorrisModel(

    @SerializedName("type") val type: String,
    @SerializedName("value") val value: List<Value>
)
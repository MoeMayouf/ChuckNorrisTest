package com.mayouf.chucknorristest.domain

import com.google.gson.annotations.SerializedName

data class ChuckNorrisModel(

    @SerializedName("type") val type: String,
    @SerializedName("value") val value: List<Value>
)
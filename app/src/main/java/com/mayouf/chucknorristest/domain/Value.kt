package com.mayouf.chucknorristest.domain

import com.google.gson.annotations.SerializedName

data class Value(

    @SerializedName("id") val id: Int,
    @SerializedName("joke") val joke: String,
    @SerializedName("categories") val categories: List<String>
)
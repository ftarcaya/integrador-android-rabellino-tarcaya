package com.example.notbored

import com.google.gson.annotations.SerializedName

data class ActivityResponse (
    @SerializedName("type") val category: String,
    @SerializedName("activity") val name: String,
    @SerializedName("price") val price: String
)

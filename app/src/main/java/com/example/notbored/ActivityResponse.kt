package com.example.notbored

import com.google.gson.annotations.SerializedName

data class ActivityResponse (
    @SerializedName("type") val category: String,
    @SerializedName("activity") val name: String,
    @SerializedName("price") val price: String,
    @SerializedName("accessibility") val accessibility: String,
    @SerializedName("participants") val participants: String,
    @SerializedName("key") val key: String,
    @SerializedName("error") val error: String,

)

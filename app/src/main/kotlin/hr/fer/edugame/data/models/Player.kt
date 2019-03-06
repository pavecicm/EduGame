package hr.fer.edugame.data.models

import com.google.gson.annotations.SerializedName

class Player(
    @SerializedName("id") val id: String,
    @SerializedName("word") var word: String,
    @SerializedName("number") var number: Int,
    @SerializedName("score") var score: Int
)
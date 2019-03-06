package hr.fer.edugame.data.models

import com.google.gson.annotations.SerializedName

class Word(
    @SerializedName("word") val word: String
)

class Result(
    @SerializedName("definition") val definition: String
)
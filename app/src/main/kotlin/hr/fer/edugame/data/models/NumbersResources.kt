package hr.fer.edugame.data.models

import com.google.gson.annotations.SerializedName

class StartingNumbers(
    @SerializedName("wantedNumber") val wantendNumber: Int,
    @SerializedName("startingNumbers") val numbers: List<Int>
)

class NumberResult(
    @SerializedName("finalNumber") val finalNumber: Int
)


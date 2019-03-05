package hr.fer.edugame.extensions

import java.util.Random

fun IntRange.random() =
    Random().nextInt((endInclusive + 1) - start) +  start
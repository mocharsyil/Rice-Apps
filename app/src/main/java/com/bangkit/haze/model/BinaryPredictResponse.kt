package com.bangkit.haze.model

class BinaryPredictResponse ( val predictions: List<Prediction>
    )

    data class Prediction(
        val label: String,
        val confidence: Float
    )
package com.bangkit.haze.model

data class MulticlassPredictResponse(
    val predictions: List<MulticlassPrediction>
)

data class MulticlassPrediction(
    val label: String,
    val confidence: Float
)
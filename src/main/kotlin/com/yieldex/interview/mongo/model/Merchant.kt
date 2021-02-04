package com.yieldex.interview.mongo.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "merchants")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Merchant(
        val merchantId: UUID,
        val name: String,
        val latitude: Float,
        val longitude: Float,
        val address: String? = ""
)

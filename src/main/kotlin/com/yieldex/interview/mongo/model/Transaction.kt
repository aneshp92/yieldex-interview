package com.yieldex.interview.mongo.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "transactions")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Transaction(
        val userId: UUID,
        val merchantId: UUID,
        val amountInCents: Int,
        val timestamp: Long
)

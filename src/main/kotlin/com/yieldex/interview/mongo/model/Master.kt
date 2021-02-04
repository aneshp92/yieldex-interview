package com.yieldex.interview.mongo.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection="master")
@JsonIgnoreProperties(ignoreUnknown = true)
class Master (
    @Id
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var walletId: String,
    var longitude: Float,
    var latitude: Float,
    var merchant: String,
    var amountInCents: Int,
    var createdAt: Long
)
package com.yieldex.interview.mongo.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
        val userId: UUID,
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
)

package com.yieldex.interview.mongo.model

import java.util.*

data class UserBalance(
    val userId: UUID,
    val balance: Int
)

package com.yieldex.interview.data.model.postgres

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Master(
        var firstName: String,
        var lastName: String,
        var email: String,
        var password: String,
        var walletId: String,
        var longitude: Float,
        var latitude: Float,
        var merchant: String,
        var amountInCents: Int,
        var createdAt: Long,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = -1
) {
    private constructor() : this("", "", "", "", "", 0F, 0F, "", 0, 0L)
}
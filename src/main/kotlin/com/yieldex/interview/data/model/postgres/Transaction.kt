package com.yieldex.interview.data.model.postgres

import javax.persistence.*

@Entity
@Table(name="transactions")
class Transaction(
        val userId: Long,
        val merchantId: Long,
        val amountInCents: Int,
        val timestamp: Long,
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1
) {
    private constructor() : this(0L, 0L, 0, 0L)
}
package com.yieldex.interview.data.model.postgres

import javax.persistence.*

@Entity
@Table(name="merchants")
class Merchant(
        val name: String,
        val latitude: Float,
        val longitude: Float,
        val address: String? = "",
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1

) {
    private constructor() : this("", 0F, 0F, "")
}
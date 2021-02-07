package com.yieldex.interview.data.repository

import com.yieldex.interview.data.model.postgres.Master
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterRepository: CrudRepository<Master, String> {
    fun findByEmail(email: String): List<Master>
    fun findByEmailAndMerchant(email: String, merchant: String): List<Master>
    fun findByEmailAndCreatedAtBetween(email: String, startTime: Long, endTime: Long): List<Master>
}
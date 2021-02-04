package com.yieldex.interview.mongo.repository

import com.yieldex.interview.mongo.model.Transaction
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TransactionRepository: MongoRepository<Transaction, String> {
    fun findById(id: UUID): List<Transaction>
}
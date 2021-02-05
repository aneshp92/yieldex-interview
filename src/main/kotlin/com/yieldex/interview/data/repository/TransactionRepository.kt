package com.yieldex.interview.data.repository

import com.yieldex.interview.data.model.postgres.Transaction
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: CrudRepository<Transaction, String> {
    fun findById(id: ObjectId): List<Transaction>
}
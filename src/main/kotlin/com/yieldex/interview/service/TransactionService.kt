package com.yieldex.interview.service

import com.yieldex.interview.data.model.postgres.Transaction
import com.yieldex.interview.data.repository.MasterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService (
    @Autowired private val masterRepo: MasterRepository
) {

    fun getTransactionsBetween(userId: UUID, start: Long, end: Long): List<Transaction> {
        return emptyList()
    }

    fun getTransactionsByMerchant(userId: UUID, merchantId: UUID): List<Transaction> {
        return emptyList()
    }

}
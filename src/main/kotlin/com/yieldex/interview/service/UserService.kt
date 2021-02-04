package com.yieldex.interview.service

import com.yieldex.interview.mongo.model.Transaction
import com.yieldex.interview.mongo.model.UserBalance
import com.yieldex.interview.mongo.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class UserService(
        @Autowired private val transactionsRepo: TransactionRepository
) {

    fun getUserBalance(userId: UUID): UserBalance {
        val transactions: List<Transaction> = transactionsRepo.findById(userId)
        return UserBalance(
                userId = userId,
                balance = transactions.map { it.amountInCents }.sum()
        )
    }

}
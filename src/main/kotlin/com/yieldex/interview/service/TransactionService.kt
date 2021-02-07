package com.yieldex.interview.service

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.postgres.Transaction
import com.yieldex.interview.data.model.request.TransactionRequest
import com.yieldex.interview.data.model.response.FilteredTransactionResponse
import com.yieldex.interview.data.model.response.TransactionResponse
import com.yieldex.interview.data.repository.MasterRepository
import com.yieldex.interview.data.repository.MerchantRepository
import com.yieldex.interview.data.repository.TransactionRepository
import com.yieldex.interview.data.repository.UserRepository
import com.yieldex.interview.exceptions.NotFoundException
import com.yieldex.interview.extensions.MasterExtension.Companion.toFilteredTransactionResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TransactionService (
    @Autowired private val masterRepo: MasterRepository,
    @Autowired private val userRepo: UserRepository,
    @Autowired private val userService: UserService,
    @Autowired private val transactionRepo: TransactionRepository,
    @Autowired private val merchantRepo: MerchantRepository,
) {

    fun getTransactionsBetween(userId: Long, start: Long, end: Long): List<FilteredTransactionResponse> {
        val email: String = userRepo.findById(userId)?.email
            ?: throw NotFoundException("User for userId: $userId does not have any transactions")
        val transactions: List<Master> = masterRepo.findByEmailAndCreatedAtBetween(email, start, end)
        val result = ArrayList<FilteredTransactionResponse>()

        for (transaction in transactions) {
            val merchantId: Long = merchantRepo.findByName(transaction.merchant).id
            result.add(transaction.toFilteredTransactionResponse(userId, merchantId))
        }

        return result
    }

    fun getTransactionsByMerchant(userId: Long, merchantId: Long): List<FilteredTransactionResponse> {
        val email: String = userRepo.findById(userId)?.email
            ?: throw NotFoundException("User for userId: $userId does not have any transactions")
        val merchantName: String = merchantRepo.findById(merchantId).name
        val transactions: List<Master> = masterRepo.findByEmailAndMerchant(email, merchantName)
        val result = ArrayList<FilteredTransactionResponse>()

        for (transaction in transactions) {
            result.add(transaction.toFilteredTransactionResponse(userId, merchantId))
        }

        return result
    }

    fun authorizeTransaction(transaction: TransactionRequest): TransactionResponse {
        val userBalance: Int = userService.getUserBalance(transaction.userId).balance
        val status: String = if(transaction.amountInCents <= userBalance) "APPROVED" else "DECLINED"
        val endingBalance: Int = if("APPROVED" == status) userBalance - transaction.amountInCents else userBalance

        transactionRepo.save(Transaction(
            merchantId = transaction.merchantId,
            userId = transaction.userId,
            amountInCents = transaction.amountInCents,
            timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            status = status
        ))

        return TransactionResponse(
                status = status,
                userId = transaction.userId,
                merchantId = transaction.merchantId,
                userBalance = endingBalance
            )
    }

}
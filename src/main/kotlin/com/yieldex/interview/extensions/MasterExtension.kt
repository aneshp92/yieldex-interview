package com.yieldex.interview.extensions

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.postgres.Transaction
import com.yieldex.interview.data.model.response.FilteredTransactionResponse
import com.yieldex.interview.data.repository.MerchantRepository
import com.yieldex.interview.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired

class MasterExtension(
    @Autowired private val userRepo: UserRepository,
    @Autowired private val merchantRepo: MerchantRepository
) {

    companion object {
        fun Master.toTransactionResponse(userId: Long, merchantId: Long): Transaction {
            return Transaction(
                userId = userId,
                merchantId = merchantId,
                amountInCents = this.amountInCents,
                timestamp = this.createdAt,
                status = "APPROVED"
            )
        }

        fun Master.toFilteredTransactionResponse(userId: Long, merchantId: Long): FilteredTransactionResponse {
            return FilteredTransactionResponse(
                userId = userId,
                merchantId = merchantId,
                amountInCents = this.amountInCents,
                timestamp = this.createdAt,
                status = "APPROVED"
            )
        }
    }

}
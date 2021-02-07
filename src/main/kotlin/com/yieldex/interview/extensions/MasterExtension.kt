package com.yieldex.interview.extensions

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.response.FilteredTransactionResponse

class MasterExtension {

    companion object {
        fun Master.toFilteredTransactionResponse(userId: Long, merchantId: Long): FilteredTransactionResponse {
            return FilteredTransactionResponse(
                userId = userId,
                merchantName = this.merchant,
                merchantId = merchantId,
                amountInCents = this.amountInCents,
                timestamp = this.createdAt,
                status = "APPROVED"
            )
        }
    }

}
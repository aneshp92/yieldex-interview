package com.yieldex.interview.data.repository

import com.yieldex.interview.data.model.postgres.Merchant
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MerchantRepository: CrudRepository<Merchant, String> {
}
package com.yieldex.interview.mongo.repository

import com.yieldex.interview.mongo.model.Merchant
import org.springframework.data.mongodb.repository.MongoRepository

interface MerchantRepository: MongoRepository<Merchant, String> {
}
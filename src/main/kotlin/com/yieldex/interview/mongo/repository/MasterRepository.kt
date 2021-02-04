package com.yieldex.interview.mongo.repository

import com.yieldex.interview.mongo.model.Master
import org.springframework.data.mongodb.repository.MongoRepository

interface MasterRepository: MongoRepository<Master, String> {
}
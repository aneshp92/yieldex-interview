package com.yieldex.interview.mongo.repository

import com.yieldex.interview.mongo.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {
}
package com.yieldex.interview.data.repository

import com.yieldex.interview.data.model.postgres.Master
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterRepository: CrudRepository<Master, String> {
    fun findById(id: ObjectId): List<Master>
    fun findByEmail(email: String): List<Master>
}
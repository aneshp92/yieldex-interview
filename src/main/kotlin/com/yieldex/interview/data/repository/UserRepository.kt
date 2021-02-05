package com.yieldex.interview.data.repository

import com.yieldex.interview.data.model.postgres.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, String> {
    fun findById(id: Long): User?
    fun findByEmail(email: String): User
    fun countByEmail(email: String): Long
}
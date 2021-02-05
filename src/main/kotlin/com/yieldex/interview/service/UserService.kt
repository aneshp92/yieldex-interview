package com.yieldex.interview.service

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.postgres.User
import com.yieldex.interview.data.model.request.UserRequest
import com.yieldex.interview.data.model.response.UserBalance
import com.yieldex.interview.data.repository.MasterRepository
import com.yieldex.interview.data.repository.UserRepository
import com.yieldex.interview.exceptions.AlreadyExistsException
import com.yieldex.interview.exceptions.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
        @Autowired private val transactionsRepo: MasterRepository,
        @Autowired private val userRepo: UserRepository
) {

    fun getUserBalance(userId: Long): UserBalance {
        val user: User = userRepo.findById(userId)
                ?: throw NotFoundException("User for userId: $userId does not have any transactions")
        val transactions: List<Master> = transactionsRepo.findByEmail(user.email)
        return UserBalance(
                userId = userId,
                balance = transactions.sumBy { transaction -> transaction.amountInCents }
        )
    }

    fun createUser(userToCreate: User): User {
        if(userRepo.countByEmail(userToCreate.email) != 0L) {
            throw AlreadyExistsException("User for email: ${userToCreate.email} already exists")
        }
        val userCreated: User = User(
                email = userToCreate.email,
                lastName = userToCreate.lastName,
                firstName = userToCreate.firstName,
                password = userToCreate.password,
                id = -1
        )
        userRepo.save(userCreated)
        userCreated.apply {
            id = userRepo.findByEmail(userToCreate.email).id
        }
        return userCreated
    }

    fun updateUser(userId: Long, userToUpdate: UserRequest): User {
        val user: User = userRepo.findById(userId)
                ?: throw NotFoundException("User for userId: $userId does not exist")
        userToUpdate.firstName?.let {
            user.apply {
                firstName = it
            }
        }
        userToUpdate.lastName?.let {
            user.apply {
                lastName = it
            }
        }
        userToUpdate.email?.let {
            user.apply {
                email = it
            }
        }
        userToUpdate.password?.let {
            user.apply {
                password = it
            }
        }

        userRepo.save(user)

        return user
    }

}
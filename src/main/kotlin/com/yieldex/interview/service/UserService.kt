package com.yieldex.interview.service

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.postgres.User
import com.yieldex.interview.data.model.request.UserCreateRequest
import com.yieldex.interview.data.model.request.UserUpdateRequest
import com.yieldex.interview.data.model.response.UserBalance
import com.yieldex.interview.data.model.response.UserMerchantSummaryResponse
import com.yieldex.interview.data.repository.MasterRepository
import com.yieldex.interview.data.repository.MerchantRepository
import com.yieldex.interview.data.repository.UserRepository
import com.yieldex.interview.exceptions.AlreadyExistsException
import com.yieldex.interview.exceptions.NotFoundException
import com.yieldex.interview.extensions.StringExtensions.Companion.encrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import java.util.stream.Stream

@Service
class UserService(
        @Autowired private val transactionsRepo: MasterRepository,
        @Autowired private val userRepo: UserRepository,
        @Autowired private val merchantRepo: MerchantRepository,
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

    fun createUser(userToCreate: UserCreateRequest): User {
        if(userRepo.countByEmail(userToCreate.email) != 0L) {
            throw AlreadyExistsException("User for email: ${userToCreate.email} already exists")
        }
        val userCreated = User(
                email = userToCreate.email,
                lastName = userToCreate.lastName,
                firstName = userToCreate.firstName,
                password = userToCreate.password.encrypt(),
                id = -1
        )
        userRepo.save(userCreated)
        userCreated.apply {
            id = userRepo.findByEmail(userToCreate.email).id
        }
        return userCreated
    }

    fun updateUser(userId: Long, userUpdateToUpdate: UserUpdateRequest): User {
        val user: User = userRepo.findById(userId)
                ?: throw NotFoundException("User for userId: $userId does not exist")
        userUpdateToUpdate.firstName?.let {
            user.apply {
                firstName = it
            }
        }
        userUpdateToUpdate.lastName?.let {
            user.apply {
                lastName = it
            }
        }
        userUpdateToUpdate.email?.let {
            user.apply {
                email = it
            }
        }
        userUpdateToUpdate.password?.let {
            user.apply {
                password = it
            }
        }

        userRepo.save(user)

        return user
    }

    fun summarizeMerchants(userId: Long): List<UserMerchantSummaryResponse> {
        val email: String = userRepo.findById(userId)?.email
            ?: throw NotFoundException("User for userId: $userId does not exist")
        val transactions: List<Master> = transactionsRepo.findByEmail(email)
        val merchants: List<String> = transactions.stream().flatMap { transaction -> Stream.of(transaction.merchant) }.distinct().collect(Collectors.toList())

        val merchantSummaries = ArrayList<UserMerchantSummaryResponse>()
        merchants.forEach { merchant ->
            merchantSummaries.add(
                UserMerchantSummaryResponse(
                    merchantId = merchantRepo.findByName(merchant).id,
                    merchantName = merchant,
                    userId = userId,
                    amountSpent = transactions.filter { transaction -> merchant.equals(transaction.merchant) }.sumOf { transaction -> transaction.amountInCents }
                )
            )
        }
        return merchantSummaries
    }

}
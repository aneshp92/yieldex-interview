package com.yieldex.interview.service

import com.yieldex.interview.mongo.model.Merchant
import com.yieldex.interview.mongo.model.Master
import com.yieldex.interview.mongo.model.Transaction
import com.yieldex.interview.mongo.model.User
import com.yieldex.interview.mongo.repository.MerchantRepository
import com.yieldex.interview.mongo.repository.MasterRepository
import com.yieldex.interview.mongo.repository.TransactionRepository
import com.yieldex.interview.mongo.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashSet

@Slf4j
@Service
class DataService(
        @Autowired private val masterRepo: MasterRepository,
        @Autowired private val userRepo: UserRepository,
        @Autowired private val merchantRepo: MerchantRepository,
        @Autowired private val transactionRepo: TransactionRepository,
) {

    val logger: Logger = LoggerFactory.getLogger("DataService")

    fun normalize() {
        val data: List<Master> = masterRepo.findAll()

        logger.info("in normalize Data, data count: " + data.size)

        val users: HashSet<User> = HashSet()
        val merchants: HashSet<Merchant> = HashSet()
        val transactions: HashSet<Transaction> = HashSet()
        data.let { it ->

            it.forEach {
                if(!users.any{ user -> user.email == it.email }) {
                    val user: User = User(
                            userId = UUID.randomUUID(),
                            firstName = it.firstName,
                            lastName = it.lastName,
                            email = it.email,
                            password = it.password
                    )
                    users.add(user)
                }


                if(!merchants.any{ merchant -> merchant.name == it.merchant }) {
                    val merchant: Merchant = Merchant(
                            merchantId = UUID.randomUUID(),
                            name = it.merchant,
                            latitude = it.latitude,
                            longitude = it.longitude
                    )
                    merchants.add(merchant)
                }
            }
            userRepo.insert(users);
            merchantRepo.insert(merchants)

            logger.info("Users inserted: " + users.size)
            logger.info("Merchants inserted: " + merchants.size)

            it.forEach {
                if(!transactions.any{ transaction -> transaction.timestamp == it.createdAt }) {
                    val userId = users.first { user -> user.email == it.email }.userId
                    val merchantId = merchants.first { merchant -> merchant.name == it.merchant }.merchantId
                    val transaction: Transaction = Transaction(
                            merchantId = merchantId,
                            userId = userId,
                            amountInCents = it.amountInCents,
                            timestamp = it.createdAt
                    )
                    transactions.add(transaction)
                }
            }

            transactionRepo.insert(transactions)

            logger.info("Transactions inserted: " + transactions.size)

        }
    }
}
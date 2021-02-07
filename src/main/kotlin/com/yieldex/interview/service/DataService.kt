package com.yieldex.interview.service

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.postgres.Merchant
import com.yieldex.interview.data.model.postgres.Transaction
import com.yieldex.interview.data.model.postgres.User
import com.yieldex.interview.data.repository.MasterRepository
import com.yieldex.interview.data.repository.MerchantRepository
import com.yieldex.interview.data.repository.TransactionRepository
import com.yieldex.interview.data.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class DataService{

    @Autowired lateinit var masterRepo: MasterRepository
    @Autowired lateinit var userRepo: UserRepository
    @Autowired lateinit var merchantRepo: MerchantRepository
    @Autowired lateinit var transactionRepo: TransactionRepository

    val logger: Logger = LoggerFactory.getLogger("DataService")

    fun normalizeUsersAndMerchants() {
        val data: Iterable<Master> = masterRepo.findAll()

        logger.info("data count: " + data.count())

        val users: HashSet<User> = HashSet()
        val merchants: HashSet<Merchant> = HashSet()
        data.let { it ->

            it.forEach {
                if(!users.any{ user -> user.email == it.email }) {
                    val user = User(
                            firstName = it.firstName,
                            lastName = it.lastName,
                            email = it.email,
                            password = it.password,
                            id = -1
                    )
                    users.add(user)
                }


                if(!merchants.any{ merchant -> merchant.name == it.merchant }) {
                    val merchant: Merchant = Merchant(
                            name = it.merchant,
                            latitude = it.latitude,
                            longitude = it.longitude
                    )
                    merchants.add(merchant)
                }
            }
            userRepo.saveAll(users)
            merchantRepo.saveAll(merchants)

            logger.info("Users inserted: " + users.size)
            logger.info("Merchants inserted: " + merchants.size)

        }
    }

    fun normalizeTransactions() {
        val data: Iterable<Master> = masterRepo.findAll()
        val users: Iterable<User> = userRepo.findAll()
        val merchants: Iterable<Merchant> = merchantRepo.findAll()

        logger.info("data count: " + data.count())

        val transactions: HashSet<Transaction> = HashSet()
        data.let { it ->

            it.forEach {
                if(!transactions.any{ transaction -> transaction.timestamp == it.createdAt }) {
                    val userId = users.first { user -> user.email == it.email }.id
                    val merchantId = merchants.first { merchant -> merchant.name == it.merchant }.id

                    transactions.add(Transaction(
                        userId = userId,
                        merchantId = merchantId,
                        amountInCents = it.amountInCents,
                        timestamp = it.createdAt,
                        status = "APPROVED",
                    ))
                }
            }

            transactionRepo.saveAll(transactions)

            logger.info("Transactions inserted: " + transactions.size)
        }
    }
}
package com.yieldex.interview.service

import com.yieldex.interview.data.repository.MasterRepository
import com.yieldex.interview.data.repository.MerchantRepository
import com.yieldex.interview.data.repository.TransactionRepository
import com.yieldex.interview.data.repository.UserRepository
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito

class TransactionServiceTest {

    @Mock
    private val masterRepo: MasterRepository = Mockito.mock(MasterRepository::class.java)

    @Mock
    private val transactionsRepo: TransactionRepository = Mockito.mock(TransactionRepository::class.java)

    @Mock
    private val userRepo: UserRepository = Mockito.mock(UserRepository::class.java)

    @Mock
    private val userService: UserService = Mockito.mock(UserService::class.java)

    @Mock
    private val merchantRepo: MerchantRepository = Mockito.mock(MerchantRepository::class.java)

    @InjectMocks
    private lateinit var transactionService: TransactionService

    @Test
    fun test_getTransactionsBetween_valid() {

    }

}
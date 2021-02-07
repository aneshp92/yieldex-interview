package com.yieldex.interview.service

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.postgres.Merchant
import com.yieldex.interview.data.model.postgres.User
import com.yieldex.interview.data.model.request.TransactionRequest
import com.yieldex.interview.data.model.response.FilteredTransactionResponse
import com.yieldex.interview.data.model.response.TransactionResponse
import com.yieldex.interview.data.model.response.UserBalance
import com.yieldex.interview.data.repository.MasterRepository
import com.yieldex.interview.data.repository.MerchantRepository
import com.yieldex.interview.data.repository.TransactionRepository
import com.yieldex.interview.data.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class TransactionServiceTest {

    @Mock
    private val masterRepo: MasterRepository = Mockito.mock(MasterRepository::class.java)

    @Mock
    private val userRepo: UserRepository = Mockito.mock(UserRepository::class.java)

    @Mock
    private val userService: UserService = Mockito.mock(UserService::class.java)

    @Mock
    private val transactionsRepo: TransactionRepository = Mockito.mock(TransactionRepository::class.java)

    @Mock
    private val merchantRepo: MerchantRepository = Mockito.mock(MerchantRepository::class.java)

    @InjectMocks
    private lateinit var transactionService: TransactionService

    @Test
    fun test_getTransactionsBetween_valid() {
        val user = User("first", "last", "test@test.com", "pass", 0)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val transactions = ArrayList<Master>()
        transactions.add(Master("", "", "", "", "", 0F, 0F, "",  11, 12))
        transactions.add(Master("", "", "", "", "", 0F, 0F, "",  9, 123))
        Mockito.`when`(masterRepo.findByEmailAndCreatedAtBetween(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(transactions)

        val merchant = Merchant("", 0F, 0F, "", 1)
        Mockito.`when`(merchantRepo.findByName(ArgumentMatchers.anyString())).thenReturn(merchant)

        val filteredTransactions: List<FilteredTransactionResponse> = transactionService.getAllTransactionsBetween(1, 0, 200)
        assertEquals(2, filteredTransactions.size)
        assertEquals(12, filteredTransactions[0].timestamp)
        assertEquals(123, filteredTransactions[1].timestamp)

    }

    @Test
    fun test_getTransactionsBetween_valid2() {
        val user = User("first", "last", "test@test.com", "pass", 0)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val transactions = ArrayList<Master>()
        transactions.add(Master("", "", "", "", "", 0F, 0F, "",  11, 12))
        Mockito.`when`(masterRepo.findByEmailAndCreatedAtBetween(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(transactions)

        val merchant = Merchant("", 0F, 0F, "", 1)
        Mockito.`when`(merchantRepo.findByName(ArgumentMatchers.anyString())).thenReturn(merchant)

        val filteredTransactions: List<FilteredTransactionResponse> = transactionService.getAllTransactionsBetween(1, 0, 100)
        assertEquals(1, filteredTransactions.size)
        assertEquals(12, filteredTransactions[0].timestamp)

    }

    @Test
    fun test_getTransactionsByMerchant_valid() {
        val user = User("first", "last", "test@test.com", "pass", 0)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val merchant = Merchant("merchant", 0F, 0F, "", 1)
        Mockito.`when`(merchantRepo.findById(ArgumentMatchers.anyLong())).thenReturn(merchant)

        val transactions = ArrayList<Master>()
        transactions.add(Master("", "", "", "", "", 0F, 0F, "merchant",  11, 12))
        transactions.add(Master("", "", "", "", "", 0F, 0F, "merchant",  9, 123))
        Mockito.`when`(masterRepo.findByEmailAndMerchant(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(transactions)

        val filteredTransactions: List<FilteredTransactionResponse> = transactionService.getAllTransactionsByMerchant(1, 1)
        assertEquals(2, filteredTransactions.size)
        assertEquals("merchant", filteredTransactions[0].merchantName)
        assertEquals("merchant", filteredTransactions[1].merchantName)
    }

    @Test
    fun test_authorizeTransaction_approved() {
        val userBalance = UserBalance(1, 100)
        Mockito.`when`(userService.getUserBalance(ArgumentMatchers.anyLong())).thenReturn(userBalance)

        val transactionRequest: TransactionRequest = TransactionRequest(1, 1, 10, 1)
        val transactionResponse: TransactionResponse = transactionService.authorizeTransaction(transactionRequest)
        assertEquals("APPROVED", transactionResponse.status)
        assertEquals(90, transactionResponse.userBalance)
        assertEquals(1, transactionResponse.userId)
    }

    @Test
    fun test_authorizeTransaction_declined() {
        val userBalance = UserBalance(1, 10)
        Mockito.`when`(userService.getUserBalance(ArgumentMatchers.anyLong())).thenReturn(userBalance)

        val transactionRequest: TransactionRequest = TransactionRequest(1, 1, 100, 1)
        val transactionResponse: TransactionResponse = transactionService.authorizeTransaction(transactionRequest)
        assertEquals("DECLINED", transactionResponse.status)
        assertEquals(10, transactionResponse.userBalance)
        assertEquals(1, transactionResponse.userId)
    }
}
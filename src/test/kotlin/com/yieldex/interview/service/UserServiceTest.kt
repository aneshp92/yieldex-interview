package com.yieldex.interview.service

import com.yieldex.interview.data.model.postgres.Master
import com.yieldex.interview.data.model.postgres.Merchant
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
import lombok.extern.slf4j.Slf4j
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest
@Slf4j
class UserServiceTest {

    @Mock
    private val transactionsRepo: MasterRepository = Mockito.mock(MasterRepository::class.java)

    @Mock
    private val userRepo: UserRepository = Mockito.mock(UserRepository::class.java)

    @Mock
    private val merchantRepo: MerchantRepository = Mockito.mock(MerchantRepository::class.java)

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun test_getUserBalance_withValidUser() {
        val transactions = ArrayList<Master>()
        transactions.add(Master("", "", "", "", "", 0F, 0F, "",  11, 123))
        transactions.add(Master("", "", "", "", "", 0F, 0F, "",  9, 123))
        Mockito.`when`(transactionsRepo.findByEmail(ArgumentMatchers.anyString())).thenReturn(transactions)

        val user: User = User("first", "last", "test@test.com", "pass", 0)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val userBalance: UserBalance = userService.getUserBalance(ArgumentMatchers.anyLong())
        assertEquals(userBalance.balance, 20)
    }

    @Test
    fun test_getUserBalance_withUserNotFound() {
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenThrow(NotFoundException("User for userId: 0 does not have any transactions"))

        val exception = assertThrows<NotFoundException> {
            userService.getUserBalance(ArgumentMatchers.anyLong())
        }
        assertEquals("User for userId: 0 does not have any transactions", exception.message)
    }

    @Test
    fun test_CreateUser_new() {
        val userCreateRequest = UserCreateRequest("first", "last", "email", "pass")
        val user = User("first", "last", "email", "pass", 123)
        Mockito.`when`(userRepo.countByEmail(ArgumentMatchers.anyString())).thenReturn(0)
        Mockito.`when`(userRepo.findByEmail(ArgumentMatchers.anyString())).thenReturn(user)
        val userCreated: User = userService.createUser(userCreateRequest)

        assertEquals("first", userCreated.firstName)
        assertEquals("last", userCreated.lastName)
        assertEquals("email", userCreated.email)
        assertEquals("O0RdbWHLsUg=", userCreated.password)
        assertEquals(123, userCreated.id)
    }

    @Test
    fun test_createUser_existing() {
        val userCreate = UserCreateRequest("first", "last", "email", "pass")
        Mockito.`when`(userRepo.countByEmail(ArgumentMatchers.anyString())).thenReturn(1)

        val exception = assertThrows<AlreadyExistsException> {
            userService.createUser(userCreate)
        }
        assertEquals("User for email: email already exists", exception.message)
    }

    @Test
    fun test_updateUser_firstName() {
        val updateUser: UserUpdateRequest = UserUpdateRequest("test",null, null, null)
        val user: User = User("first", "last", "test@test.com", "pass", 123)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val userUpdated: User = userService.updateUser(123, updateUser)
        assertEquals("test", userUpdated.firstName)
        assertEquals("last", userUpdated.lastName)
        assertEquals("test@test.com", userUpdated.email)
        assertEquals("pass", userUpdated.password)
        assertEquals(123, userUpdated.id)
    }

    @Test
    fun test_updateUser_lastName() {
        val updateUser: UserUpdateRequest = UserUpdateRequest(null,"test", null, null)
        val user: User = User("first", "last", "test@test.com", "pass", 123)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val userUpdated: User = userService.updateUser(123, updateUser)
        assertEquals("first", userUpdated.firstName)
        assertEquals("test", userUpdated.lastName)
        assertEquals("test@test.com", userUpdated.email)
        assertEquals("pass", userUpdated.password)
        assertEquals(123, userUpdated.id)
    }

    @Test
    fun test_updateUser_email() {
        val updateUser: UserUpdateRequest = UserUpdateRequest(null,null, "test", null)
        val user: User = User("first", "last", "test@test.com", "pass", 123)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val userUpdated: User = userService.updateUser(123, updateUser)
        assertEquals("first", userUpdated.firstName)
        assertEquals("last", userUpdated.lastName)
        assertEquals("test", userUpdated.email)
        assertEquals("pass", userUpdated.password)
        assertEquals(123, userUpdated.id)
    }

    @Test
    fun test_updateUser_pass() {
        val updateUser: UserUpdateRequest = UserUpdateRequest(null,null, null, "test")
        val user: User = User("first", "last", "test@test.com", "pass", 123)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val userUpdated: User = userService.updateUser(123, updateUser)
        assertEquals("first", userUpdated.firstName)
        assertEquals("last", userUpdated.lastName)
        assertEquals("test@test.com", userUpdated.email)
        assertEquals("test", userUpdated.password)
        assertEquals(123, userUpdated.id)
    }

    @Test
    fun test_updateUser_userNotFound() {
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenThrow(NotFoundException("User for userId: 0 does not have any transactions"))

        val exception = assertThrows<NotFoundException> {
            userService.getUserBalance(ArgumentMatchers.anyLong())
        }
        assertEquals("User for userId: 0 does not have any transactions", exception.message)
    }

    @Test
    fun test_summarizeMerchants_userNotFound() {
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenThrow(NotFoundException("User for userId: 0 does not have any transactions"))

        val exception = assertThrows<NotFoundException> {
            userService.getUserBalance(ArgumentMatchers.anyLong())
        }
        assertEquals("User for userId: 0 does not have any transactions", exception.message)
    }

    @Test
    fun test_summarizeMerchants_valid() {
        val user = User("first", "last", "test@test.com", "pass", 0)
        Mockito.`when`(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(user)

        val transactions = ArrayList<Master>()
        transactions.add(Master("", "", "", "", "", 0F, 0F, "merchant1",  1, 123))
        transactions.add(Master("", "", "", "", "", 0F, 0F, "merchant1",  11, 123))
        transactions.add(Master("", "", "", "", "", 0F, 0F, "merchant2",  9, 123))
        transactions.add(Master("", "", "", "", "", 0F, 0F, "merchant2",  -5, 123))
        Mockito.`when`(transactionsRepo.findByEmail(ArgumentMatchers.anyString())).thenReturn(transactions)

        val merchant = Merchant("", 0F, 0F, "", 1)
        Mockito.`when`(merchantRepo.findByName(ArgumentMatchers.anyString())).thenReturn(merchant)

        val merchantSummaries: List<UserMerchantSummaryResponse> = userService.summarizeMerchants(123)

        assertEquals(2, merchantSummaries.size)
        assertEquals("merchant1", merchantSummaries[0].merchantName)
        assertEquals(12, merchantSummaries[0].amountSpent)
        assertEquals("merchant2", merchantSummaries[1].merchantName)
        assertEquals(4, merchantSummaries[1].amountSpent)
    }
}
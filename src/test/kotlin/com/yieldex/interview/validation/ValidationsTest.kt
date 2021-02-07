package com.yieldex.interview.validation

import com.yieldex.interview.data.model.request.TransactionRequest
import com.yieldex.interview.data.model.request.UserCreateRequest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ValidationsTest {

    private lateinit var validations: Validations

    @BeforeEach
    fun beforeEach() {
        validations = Validations()
    }

    @Test
    fun test_isEmailValid_valid() {
        val isValid: Boolean = validations.isEmailValid("test@test.com")

        assertTrue(isValid)
    }

    @Test
    fun test_isEmailValid_invalid() {
        val isValid: Boolean = validations.isEmailValid("testtest.com")

        assertFalse(isValid)
    }

    @Test
    fun test_isValidTransaction_valid() {
        val transaction = TransactionRequest(1, 1, 10, 1)
        val isValid: Boolean = validations.isValidTransaction(transaction)

        assertTrue(isValid)
    }

    @Test
    fun test_validUserToCreate_valid() {
        val userToCreate = UserCreateRequest("first", "last", "test@test.com", "pass")
        val isValid: Boolean = validations.validUserToCreate(userToCreate)

        assertTrue(isValid)
    }

    @Test
    fun test_validUserToCreate_invalid() {
        val userToCreate = UserCreateRequest("", "", "", "")
        val isValid: Boolean = validations.validUserToCreate(userToCreate)

        assertFalse(isValid)
    }
}
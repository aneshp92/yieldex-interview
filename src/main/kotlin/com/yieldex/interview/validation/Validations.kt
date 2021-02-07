package com.yieldex.interview.validation

import com.yieldex.interview.data.model.request.TransactionRequest
import com.yieldex.interview.data.model.request.UserCreateRequest
import org.springframework.stereotype.Component

@Component
class Validations {
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";

    fun validUserToCreate(user: UserCreateRequest): Boolean {
        if(user.email.isEmpty()
                || user.firstName.isEmpty()
                || user.lastName.isEmpty()
                || user.password.isEmpty()
                || !isEmailValid(user.email)
        ) {
            return false
        }
        return true
    }

    fun isValidTransaction(transaction: TransactionRequest): Boolean {
        if(transaction.userId == null
            || transaction.merchantId == null) {
            return false
        }
        return true
    }

    fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email)
    }

}
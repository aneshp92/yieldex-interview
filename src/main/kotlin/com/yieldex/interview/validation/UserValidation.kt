package com.yieldex.interview.validation

import com.yieldex.interview.data.model.request.UserRequest
import org.springframework.stereotype.Component

@Component
class UserValidation {

    fun validUser(user: UserRequest): Boolean {
        if(user.email == null
                || user.firstName == null
                || user.lastName == null
                || user.password == null
        ) {
            return false;
        }
        return true;
    }

}
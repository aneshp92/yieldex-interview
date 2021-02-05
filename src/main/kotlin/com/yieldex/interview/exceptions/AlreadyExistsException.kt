package com.yieldex.interview.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.CONFLICT)
class AlreadyExistsException: RuntimeException {
    constructor(message: String): super(message)
}
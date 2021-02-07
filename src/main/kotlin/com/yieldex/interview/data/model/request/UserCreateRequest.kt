package com.yieldex.interview.data.model.request

import org.jetbrains.annotations.NotNull


data class UserCreateRequest(
        @field:NotNull
        var firstName: String,
        @field:NotNull
        var lastName: String,
        @field:NotNull
        var email: String,
        @field:NotNull
        var password: String
)

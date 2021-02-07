package com.yieldex.interview.data.model.request

data class UserUpdateRequest(
        var firstName: String?,
        var lastName: String?,
        var email: String?,
        var password: String?
)

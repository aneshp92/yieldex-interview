package com.yieldex.interview.controller

import com.yieldex.interview.mongo.model.User
import com.yieldex.interview.mongo.model.UserBalance
import com.yieldex.interview.service.DataService
import com.yieldex.interview.service.UserService
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user")
@Slf4j
class UserController(
        @Autowired private val userService: UserService
) {

    val logger: Logger = LoggerFactory.getLogger("UserController")

    @GetMapping("/balance/{id}")
    fun getUserBalance(@PathVariable id: UUID): UserBalance {
        logger.info("entering getBalance method")
        return userService.getUserBalance(id)
    }

    @PostMapping("/")
    fun createUser(): User? {
        return null;
    }

    @PatchMapping("/{id}")
    fun updateUser(@PathVariable id: String): User? {
        return null;
    }


}
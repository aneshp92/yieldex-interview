package com.yieldex.interview.controller

import com.yieldex.interview.data.model.postgres.User
import com.yieldex.interview.data.model.request.UserRequest
import com.yieldex.interview.service.UserService
import lombok.extern.slf4j.Slf4j
import org.jetbrains.annotations.NotNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Slf4j
class UserController(
        @Autowired private val userService: UserService,
) {

    val logger: Logger = LoggerFactory.getLogger("UserController")

    @GetMapping("/{id}/balance")
    fun getUserBalance(@PathVariable @NotNull id: String): ResponseEntity<*> {
        logger.info("entering getBalance method with id: $id")
        return try {
            ResponseEntity(userService.getUserBalance(id.toLong()), HttpStatus.OK)
        } catch (e: NumberFormatException) {
            ResponseEntity("Please provide valid userId", HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/")
    @ResponseBody
    fun createUser(@RequestBody @NotNull userToCreate: User): ResponseEntity<*> {
        return if(userToCreate != null) {
            val userCreated: User = userService.createUser(userToCreate);
            ResponseEntity(userCreated, HttpStatus.OK)
        } else {
            ResponseEntity("Please provide valid user to create", HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/{id}")
    @ResponseBody
    fun updateUser(@PathVariable @NotNull id: String, @RequestBody @NotNull userToUpdate: UserRequest): ResponseEntity<*> {
        return try {
            ResponseEntity(userService.updateUser(id.toLong(), userToUpdate), HttpStatus.OK)
        } catch (e: NumberFormatException) {
            ResponseEntity("Please provide valid userId to update", HttpStatus.BAD_REQUEST)
        }
    }


}
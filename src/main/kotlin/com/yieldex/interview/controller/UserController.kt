package com.yieldex.interview.controller

import com.yieldex.interview.data.model.postgres.User
import com.yieldex.interview.data.model.request.UserCreateRequest
import com.yieldex.interview.data.model.request.UserUpdateRequest
import com.yieldex.interview.data.model.response.FilteredTransactionResponse
import com.yieldex.interview.service.TransactionService
import com.yieldex.interview.service.UserService
import com.yieldex.interview.validation.Validations
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
    @Autowired private val validations: Validations,
    @Autowired private val transactionService: TransactionService
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
    fun createUser(@RequestBody @NotNull userToCreate: UserCreateRequest): ResponseEntity<*> {
        return if(validations.validUserToCreate(userToCreate)) {
            val userCreated: User = userService.createUser(userToCreate);
            ResponseEntity(userCreated, HttpStatus.OK)
        } else {
            ResponseEntity("Please provide valid user to create", HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/{id}")
    @ResponseBody
    fun updateUser(@PathVariable @NotNull id: String, @RequestBody @NotNull userUpdateToUpdate: UserUpdateRequest): ResponseEntity<*> {
        return try {
            ResponseEntity(userService.updateUser(id.toLong(), userUpdateToUpdate), HttpStatus.OK)
        } catch (e: NumberFormatException) {
            ResponseEntity("Please provide valid userId to update", HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}/merchantSummary")
    @ResponseBody
    fun userMerchantSummary(@PathVariable @NotNull id: String): ResponseEntity<*> {
        return try {
            ResponseEntity(userService.summarizeMerchants(id.toLong()), HttpStatus.OK)
        } catch (e: NumberFormatException) {
            ResponseEntity("Please provide valid userId to update", HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{userId}/transactions")
    fun getTransactionsFiltered(@PathVariable @NotNull userId: String,
                                @RequestParam start: Long?,
                                @RequestParam end: Long?,
                                @RequestParam merchantId: String?,
                                @RequestParam pageSize: Int?,
                                @RequestParam page: Int?
    ): ResponseEntity<*> {
        try {

            val userIdLong: Long = userId.toLong()
            if(start != null && end != null) {
                if(start > end) return ResponseEntity("Please make sure start time is before end time", HttpStatus.BAD_REQUEST)

                return if(pageSize == null) {
                    val transactions: List<FilteredTransactionResponse> =
                        transactionService.getAllTransactionsBetween(userIdLong, start, end)
                    ResponseEntity(transactions, HttpStatus.OK)
                } else {
                    val transactions =
                        transactionService.getPaginatedTransactionsBetween(userIdLong, start, end, pageSize, page ?: 1)
                    ResponseEntity(transactions, HttpStatus.OK)
                }
            } else if(merchantId != null) {
                try{
                    val merchantIdLong: Long = merchantId.toLong()

                    return if(pageSize == null) {
                        val transactions: List<FilteredTransactionResponse> =
                            transactionService.getAllTransactionsByMerchant(userIdLong, merchantIdLong)
                        ResponseEntity(transactions, HttpStatus.OK)
                    } else {
                        val transactions =
                            transactionService.getPaginatedTransactionsByMerchant(userIdLong, merchantIdLong, pageSize, page ?: 0)
                        ResponseEntity(transactions, HttpStatus.OK)
                    }
                } catch (e: NumberFormatException) {
                    ResponseEntity("Please provide valid userId and merchantId", HttpStatus.BAD_REQUEST)
                }
            }
            return ResponseEntity("Please provide proper filters", HttpStatus.BAD_REQUEST)
        } catch (e: NumberFormatException) {
            return ResponseEntity("Please provide valid userId", HttpStatus.BAD_REQUEST)
        }
    }

}
package com.yieldex.interview.controller

import com.yieldex.interview.data.model.postgres.Transaction
import com.yieldex.interview.service.TransactionService
import lombok.extern.slf4j.Slf4j
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/transactions")
@Slf4j
class TransactionsController(
        @Autowired private val transactionService: TransactionService,
) {

    @GetMapping("/{userId}")
    fun getTransactionsBetween(@PathVariable @NotNull userId: String, @RequestParam @NotNull start: Long, @RequestParam @NotNull end: Long): ResponseEntity<*> {
        return try {
            val userIdUUID: UUID = UUID.fromString(userId)
            val transactions: List<Transaction> = transactionService.getTransactionsBetween(userIdUUID, start, end)
            return ResponseEntity(transactions, HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity("Please provide valid userId", HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{userId}/merchant/{merchantId}")
    fun getTransactionsByMerchant(@PathVariable @NotNull userId: String, @PathVariable @NotNull merchantId: String): ResponseEntity<*> {
        return try {
            val userIdUUID: UUID = UUID.fromString(userId)
            val merchantIdUUID: UUID = UUID.fromString(merchantId)
            val transactions: List<Transaction> = transactionService.getTransactionsByMerchant(userIdUUID, merchantIdUUID)
            return ResponseEntity(transactions, HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity("Please provide valid userId", HttpStatus.BAD_REQUEST)
        }
    }

}
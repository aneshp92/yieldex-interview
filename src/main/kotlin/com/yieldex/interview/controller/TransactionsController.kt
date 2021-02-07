package com.yieldex.interview.controller

import com.yieldex.interview.data.model.request.TransactionRequest
import com.yieldex.interview.service.TransactionService
import com.yieldex.interview.validation.Validations
import lombok.extern.slf4j.Slf4j
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
@Slf4j
class TransactionsController(
        @Autowired private val transactionService: TransactionService,
        @Autowired private val validations: Validations,
) {

    @PostMapping("/authorize")
    fun authorizeTransaction(@RequestBody @NotNull transaction: TransactionRequest): ResponseEntity<*> {
        return if(!validations.isValidTransaction(transaction)) {
            ResponseEntity("Please provide a valid transaction", HttpStatus.BAD_REQUEST)
        } else {
            ResponseEntity(transactionService.authorizeTransaction(transaction), HttpStatus.OK)
        }

    }

}
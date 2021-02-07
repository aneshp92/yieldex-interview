package com.yieldex.interview.controller

import com.yieldex.interview.data.model.request.TransactionRequest
import com.yieldex.interview.data.model.response.FilteredTransactionResponse
import com.yieldex.interview.service.TransactionService
import com.yieldex.interview.validation.Validations
import lombok.extern.slf4j.Slf4j
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
@Slf4j
class TransactionsController(
        @Autowired private val transactionService: TransactionService,
        @Autowired private val validations: Validations,
) {

    @GetMapping("/")
    fun getTransactionsFiltered(@RequestParam @NotNull userId: String, @RequestParam start: Long?, @RequestParam end: Long?, @RequestParam merchantId: String?): ResponseEntity<*> {
        try {
            val userIdLong: Long = userId.toLong()
            if(start != null && end != null) {
                if(start > end) return ResponseEntity("Please make sure start time is before end time", HttpStatus.BAD_REQUEST)
                val transactions: List<FilteredTransactionResponse> = transactionService.getTransactionsBetween(userIdLong, start, end)
                return ResponseEntity(transactions, HttpStatus.OK)
            } else if(merchantId != null) {
                return try{
                    val merchantIdLong: Long = merchantId.toLong()
                    val transactions: List<FilteredTransactionResponse> = transactionService.getTransactionsByMerchant(userIdLong, merchantIdLong)
                    ResponseEntity(transactions, HttpStatus.OK)
                } catch (e: NumberFormatException) {
                    ResponseEntity("Please provide valid userId and merchantId", HttpStatus.BAD_REQUEST)
                }
            }
            return ResponseEntity("Please provide proper filters", HttpStatus.BAD_REQUEST)
        } catch (e: NumberFormatException) {
            return ResponseEntity("Please provide valid userId", HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/authorize")
    fun authorizeTransaction(@RequestBody @NotNull transaction: TransactionRequest): ResponseEntity<*> {
        return if(!validations.isValidTransaction(transaction)) {
            ResponseEntity("Please provide a valid transaction", HttpStatus.BAD_REQUEST)
        } else {
            ResponseEntity(transactionService.authorizeTransaction(transaction), HttpStatus.OK)
        }

    }

}
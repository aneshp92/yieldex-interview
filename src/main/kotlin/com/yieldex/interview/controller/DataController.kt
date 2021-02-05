package com.yieldex.interview.controller

import com.yieldex.interview.service.DataService
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/data")
@Slf4j
class DataController(
        @Autowired private val dataService: DataService
) {

    val logger: Logger = LoggerFactory.getLogger("DataController")

    @GetMapping("/normalizeUsersAndMerchants")
    fun normalizeUsersAndMerchants(): ResponseEntity<String> {
        logger.info("entering normalize user and merchants method")
        dataService.normalizeUsersAndMerchants();
        return ResponseEntity("Finished normalizing users and merchants successfully", HttpStatus.OK)
    }

    @GetMapping("/normalizeTransactions")
    fun normalizeTransactions(): ResponseEntity<String> {
        logger.info("entering normalize transactions method")
        dataService.normalizeTransactions();
        return ResponseEntity("Finished normalizing transactions successfully", HttpStatus.OK)
    }
}
package com.yieldex.interview.controller

import com.yieldex.interview.service.DataService
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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

    @GetMapping("/normalize")
    fun normalizeData(): String {
        logger.info("entering normalize data method")
        dataService.normalize();
        return "Finished normalizing Data Successfully"
    }
}
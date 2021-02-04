package com.yieldex.interview

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class YieldexInterviewApplication

fun main(args: Array<String>) {
	runApplication<YieldexInterviewApplication>(*args)
}

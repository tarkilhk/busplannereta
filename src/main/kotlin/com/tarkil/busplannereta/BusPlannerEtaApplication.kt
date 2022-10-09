package com.tarkil.busplannereta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class BusPlannerEtaApplication

fun main(args: Array<String>) {
	runApplication<BusPlannerEtaApplication>(*args)
}

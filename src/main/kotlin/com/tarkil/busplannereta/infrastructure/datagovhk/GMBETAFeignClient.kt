package com.tarkil.busplannereta.infrastructure.datagovhk

import feign.Feign
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(value = "etagmb-datagovhk", url = "https://data.etagmb.gov.hk/")
interface GMBETAFeignClient {
    @GetMapping("eta/stop/{stopId}")
    fun getETA(@PathVariable("stopId") stopId:String) : GMBETADto;
//    fun getETA(@PathVariable("stopId") stopId:String) : feign.Response;
}
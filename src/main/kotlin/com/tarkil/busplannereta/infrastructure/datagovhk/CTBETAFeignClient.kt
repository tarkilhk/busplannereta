package com.tarkil.busplannereta.infrastructure.datagovhk

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(value = "etactb-datagovhk", url = "https://rt.data.gov.hk/v1/transport/citybus-nwfb/")
interface CTBETAFeignClient {
    @GetMapping("/eta/ctb/{stopId}/{busNumber}")
    fun getETA(@PathVariable("stopId") stopId:String, @PathVariable("busNumber") busNumber:String) : CityBusETADto;
}
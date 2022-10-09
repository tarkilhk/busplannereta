package com.tarkil.busplannereta.api.admin

import com.tarkil.busplannereta.domain.bus.BusStop
import com.tarkil.busplannereta.domain.bus.BusService
import com.tarkil.busplannereta.domain.bus.BusStopGroup
import com.tarkil.busplannereta.infrastructure.datapersistence.bus.BusStopType
import feign.FeignException.NotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/bus")
class BusController(val busService: BusService) {

    @PostMapping("/bus-stops")
    fun newBusStop(@RequestParam(value="officialHKBusStopId") busStopId : String,
                   @RequestParam(value="name") name: String,
                   @RequestParam(value = "type") type:BusStopType,
                   @RequestParam(value="busNumbers") busNumbers: MutableList<String>) : ResponseEntity<String>
    {
        var response:ResponseEntity<String>
        try {
            val newBusStopId = busService.upsertNewBusStop(BusStop(officialHKBusStopId = busStopId, busStopName = name, busStopType = type, busNumbers = busNumbers))
            response = ResponseEntity("Done : id $newBusStopId", HttpStatus.OK)
        }
        catch(e: NotFound) {
            response = ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
        catch (e: Exception) {
            response = ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return(response)
    }

    @GetMapping("/groups")
    fun getGroups() : MutableList<BusStopGroup>{
        return busService.getAllBusStopGroups()
    }
}
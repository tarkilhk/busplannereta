package com.tarkil.busplannereta.api

import com.tarkil.busplannereta.domain.buseta.BusETAService
import com.tarkil.busplannereta.domain.buseta.BusETA
import feign.FeignException.NotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/bus-eta")
class BusETAController(val BusETAService: BusETAService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("")
    fun getBusETAByStopIdAndBusNumber(
            @RequestParam(value="stopId") stopId: String,
            @RequestParam(value="busNumber") busNumber:String) : BusETA {

        val response = BusETAService.getCTBETAByStopIdAndBusNumber(stopId, busNumber)
        return response
    }

    @GetMapping("bus-stop")
    fun getBusETAByStopId(
            @RequestParam(value="stopId") stopId: Long) : ResponseEntity<BusETA> {
        var response : ResponseEntity<BusETA>
        try {
            val cityBusEta = BusETAService.getAllETAByStopId(stopId)
            response = ResponseEntity.ok(cityBusEta)
            return response
        }
        catch(e:NotFound) {
            return ResponseEntity(BusETA(message = "" + e.message, isError = true), HttpStatus.NOT_FOUND)
        }
        catch(e:Exception) {
            return ResponseEntity(BusETA(message = "" + e.message, isError = true), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/group")
    fun getBusETAByGroupId(
        @RequestParam(value="groupId") groupId: Long) : BusETA {

        val response = BusETAService.getCTBETAByGroupId(groupId= groupId)
        return response
    }
}

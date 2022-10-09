package com.tarkil.busplannereta.domain.buseta

import com.fasterxml.jackson.databind.ObjectMapper
import com.tarkil.busplannereta.domain.bus.BusStop
import com.tarkil.busplannereta.domain.bus.BusService
import com.tarkil.busplannereta.infrastructure.datagovhk.CTBETAFeignClient
import com.tarkil.busplannereta.infrastructure.datagovhk.GMBETADto
import com.tarkil.busplannereta.infrastructure.datagovhk.GMBETAFeignClient
import com.tarkil.busplannereta.infrastructure.datapersistence.bus.BusStopType
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class BusETAService(private val busService: BusService, private val ctbEtaFeignClient: CTBETAFeignClient, private val gmbEtaFeignClient: GMBETAFeignClient) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun getCTBETAByStopIdAndBusNumber(stopId:String, busNumber:String): BusETA {
        logger.info("Before feign call to CTB datagovhk with params stopId:'$stopId' and busNumber:'$busNumber'")
        try {
            val citybusETADataResponse = ctbEtaFeignClient.getETA(stopId, busNumber)
            logger.info("Successfully retrieved data from datagovhk")
            return BusETA(citybusETADataResponse, busNumber)
        }
        catch (e: FeignException) {
            val errorMessage = "FeignException while trying to retrieve data from datagovhk : [${e.status()}] : ${e.message}"
            logger.error(errorMessage)
            return BusETA(message = errorMessage, isError = true)
        }
        catch(e: Exception) {
            logger.error("Exception while trying to retrieve data from datagovhk : ${e.message}")
            return BusETA(message = "Exception while trying to retrieve data from datagovhk : ${e.message}", isError = true)
        }
    }

    fun get21MGMBETAByStopId(stopId:String): BusETA {
        logger.info("Before feign call to 21M GMB datagovhk with params stopId:'$stopId'")
        try {
            val gmbetaDataDto:GMBETADto = gmbEtaFeignClient.getETA(stopId)
            logger.info("Successfully retrieved data from datagovhk")
            return BusETA(gmbetaDataDto, "21M")
        }
        catch (e: FeignException) {
            val errorMessage = "FeignException while trying to retrieve data from datagovhk : [${e.status()}] : ${e.message}"
            logger.error(errorMessage)
            return BusETA(message = errorMessage, isError = true)
        }
        catch(e: Exception) {
            logger.error("Exception while trying to retrieve data from datagovhk : ${e.message}")
            return BusETA(message = "Exception while trying to retrieve data from datagovhk : ${e.message}", isError = true)
        }
    }

    fun getAllETAByStopId(stopId: Long): BusETA {
        if(this.busService.busStopExistsById(stopId)) {
            val busStop: BusStop = busService.getBusStopByBusStopId(stopId)
            val busETA = BusETA()
            if(busStop.busStopType == BusStopType.CTB) {
                for (busNumber in busStop.busNumbers) {
                    busETA.addETAs(
                        this.getCTBETAByStopIdAndBusNumber(
                            stopId = busStop.officialHKBusStopId,
                            busNumber = busNumber
                        )
                    )
                }
            }
            else if(busStop.busStopType == BusStopType.GMB) {
                for (busNumber in busStop.busNumbers) {
                    if(busNumber == "21M") {
                        busETA.addETAs(
                            this.get21MGMBETAByStopId(
                                stopId = busStop.officialHKBusStopId
                            )
                        )
                    }
                }
            }
            return busETA
        }
        else {
            throw Exception( "Bus stop Id '$stopId' doesn't exist")
        }

    }

    fun getCTBETAByGroupId(groupId: Long): BusETA {
        val busETA = BusETA()
        if(this.busService.groupExistsById(groupId)) {
            val busGroup = this.busService.getBusStopGroupById(groupId)
            for (busStop:BusStop in busGroup.busStops)
            {
                if(busStop.busStopType == BusStopType.CTB) {
                    for (busNumber in busStop.busNumbers) {
                        busETA.addETAs(
                            this.getCTBETAByStopIdAndBusNumber(
                                stopId = busStop.officialHKBusStopId,
                                busNumber = busNumber
                            )
                        )
                    }
                }
                else if(busStop.busStopType == BusStopType.GMB) {
                    for (busNumber in busStop.busNumbers) {
                        if(busNumber == "21M") {
                            busETA.addETAs(
                                this.get21MGMBETAByStopId(
                                    stopId = busStop.officialHKBusStopId
                                )
                            )
                        }
                    }
                }
            }
        }
        return busETA
    }
}
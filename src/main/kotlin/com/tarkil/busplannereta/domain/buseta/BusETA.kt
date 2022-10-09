package com.tarkil.busplannereta.domain.buseta

import com.tarkil.busplannereta.infrastructure.datagovhk.CityBusETADto
import com.tarkil.busplannereta.infrastructure.datagovhk.GMBETADto
import java.time.ZonedDateTime


class BusETA() {
    var lastRefreshTime: ZonedDateTime = ZonedDateTime.now()
    var message : String  = ""
    val ETAs : MutableList<BusETAItem> = mutableListOf()
    var isError : Boolean = false

    constructor(message:String, isError:Boolean) : this() {
        this.message = message
        this.isError = isError
        this.lastRefreshTime = ZonedDateTime.now()
    }

    constructor(cityBusETADto: CityBusETADto, busNumber : String) : this() {
        if (cityBusETADto.data.size == 0) {
            this.lastRefreshTime = ZonedDateTime.now()
            this.message = "No bus $busNumber"
        }
        else {
            lastRefreshTime = ZonedDateTime.parse(cityBusETADto.data.first().data_timestamp)
            for(etaDataResponse in cityBusETADto.data) {
                this.ETAs.add(BusETAItem(busNumber = busNumber, data_timestamp = etaDataResponse.data_timestamp, eta = ZonedDateTime.parse(etaDataResponse.eta), etaSequence = etaDataResponse.eta_seq, remark = etaDataResponse.rmk_en))
            }
            this.message = "OK"
        }
    }

    constructor(gmbEtaDto: GMBETADto, busNumber : String) : this() {
        if (gmbEtaDto.data.size == 0) {
            this.lastRefreshTime = ZonedDateTime.now()
            this.message = "No bus $busNumber"
        }
        else {
            lastRefreshTime = ZonedDateTime.parse(gmbEtaDto.generated_timestamp)
            for(etaDataResponse in gmbEtaDto.data.first().eta) {
                this.ETAs.add(BusETAItem(busNumber = busNumber, data_timestamp = gmbEtaDto.generated_timestamp,
                    eta = ZonedDateTime.parse(etaDataResponse.timestamp), etaSequence = etaDataResponse.eta_seq,
                remark = etaDataResponse.remarks))
            }
            this.message = "OK"
        }
    }

    fun addETAs(busETA: BusETA) {
        lastRefreshTime = busETA.lastRefreshTime
        for(etaDataItem in busETA.ETAs) {
            this.ETAs.add(etaDataItem)
        }
        setRelevantValueToMessage(busETA.message)
    }

    private fun setRelevantValueToMessage(message: String) {
        if (message.equals("")) {
            if (ETAs.size == 0) {
                this.message = "No bus"
            } else {
                this.message = "OK"
            }
        }
        else {
            this.message = message
        }
    }

}
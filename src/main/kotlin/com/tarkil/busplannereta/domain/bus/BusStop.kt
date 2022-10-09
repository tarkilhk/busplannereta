package com.tarkil.busplannereta.domain.bus

import com.tarkil.busplannereta.infrastructure.datapersistence.bus.BusNumberDao
import com.tarkil.busplannereta.infrastructure.datapersistence.bus.BusStopDao
import com.tarkil.busplannereta.infrastructure.datapersistence.bus.BusStopType
import java.util.*

data class BusStop(
    var busStopId : Long = -1,
    var busStopName: String = "",
    var busStopType: BusStopType = BusStopType.CTB,
    var busNumbers: MutableList<String> = mutableListOf(),
    var officialHKBusStopId : String = "") {

    constructor(busStopDao: BusStopDao) : this(busStopId = busStopDao.busStopId,
        busStopName = busStopDao.name, busStopType= busStopDao.type,
        officialHKBusStopId = busStopDao.officialHKBusStopId, busNumbers = mutableListOf<String>()) {
        for (busNumberDao in busStopDao.busNumbers) {
            this.busNumbers.add(busNumberDao.busNumber)
        }
    }

    constructor(officialHKBusStopId: String, busStopName: String, busStopType: BusStopType,busNumbers: MutableList<String>) : this() {
        this.busStopId = -1
        this.busStopName = busStopName
        this.busStopType = busStopType
        this.busNumbers = busNumbers
        this.officialHKBusStopId = officialHKBusStopId
    }

    override fun toString(): String = "Bus Stop Name $busStopName - HK official stop Id $officialHKBusStopId [${busStopType.toString()}]"

    override fun hashCode(): Int {
        return (Objects.hash(busStopName, officialHKBusStopId))
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) return false
        val that = other as BusStop
        return this.busStopName == that.busStopName &&
                this.officialHKBusStopId == that.officialHKBusStopId
    }

    fun toDao(busNumberDaos: MutableList<BusNumberDao>): BusStopDao {
//        val bsd = BusStopDao()
//        return BusStopDao(busStopName = this.busStopName,
//            busStopType= this.busStopType,
//            officialHKBusStopId = this.officialHKBusStopId,
//            busNumbers = busNumberDaos)
        return BusStopDao(busStopName = this.busStopName,
            busStopType= this.busStopType,
            officialHKBusStopId = this.officialHKBusStopId)
    }
}
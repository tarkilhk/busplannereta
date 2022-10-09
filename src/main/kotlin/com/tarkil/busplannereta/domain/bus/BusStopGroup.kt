package com.tarkil.busplannereta.domain.bus

import com.tarkil.busplannereta.infrastructure.datapersistence.bus.BusStopGroupsDao
import java.util.*

data class BusStopGroup(
    var busStopGroupId : Long = -1,
    var busStopGroupName: String = "",
    var busStops: MutableList<BusStop> = mutableListOf())
    {
    constructor(busStopGroupsDao: BusStopGroupsDao) :
            this(
                busStopGroupId = busStopGroupsDao.groupId,
                busStopGroupName = busStopGroupsDao.name,
                busStops = mutableListOf<BusStop>()
            )
    {
        for (busStopDao in busStopGroupsDao.busStops) {
            this.busStops.add(BusStop(busStopDao = busStopDao))
        }
    }
//
//    constructor(officialHKBusStopId: String, busStopName: String, busStopType: BusStopType,busNumbers: MutableList<String>) : this() {
//        this.busStopId = -1
//        this.busStopName = busStopName
//        this.busStopType = busStopType
//        this.busNumbers = busNumbers
//        this.officialHKBusStopId = officialHKBusStopId
//    }

    override fun toString(): String = "Bus Stop Group Name $busStopGroupName"

    override fun hashCode(): Int {
        return (Objects.hash(busStopGroupName, busStopGroupId))
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) return false
        val that = other as BusStopGroup
        return this.busStopGroupName == that.busStopGroupName &&
                this.busStopGroupId == that.busStopGroupId
    }

//    fun toDao(busNumberDaos: MutableList<BusNumberDao>): BusStopDao {
//        val bsd = BusStopDao()
//        return BusStopDao(busStopName = this.busStopName,
//            busStopType= this.busStopType,
//            officialHKBusStopId = this.officialHKBusStopId,
//            busNumbers = busNumberDaos)
//        return BusStopGroupsDao("")
//    }
}
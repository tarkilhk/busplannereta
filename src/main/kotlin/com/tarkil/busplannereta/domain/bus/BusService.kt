package com.tarkil.busplannereta.domain.bus

import com.tarkil.busplannereta.infrastructure.datapersistence.bus.*
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BusService(private val busNumberRepository: BusNumberRepository, private val busStopRepository: BusStopRepository, private val busStopGroupsRepository: BusStopGroupsRepository) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun upsertNewBusStop(newBusStop: BusStop): Long {
        if (busStopRepository.existsByName(newBusStop.busStopName)) {
            logger.info("Found busStop with name ${newBusStop.busStopName} to update")

            val toBeUpdatedBusStopDao: BusStopDao = busStopRepository.getByName(newBusStop.busStopName)
            val busNumberDaos: MutableList<BusNumberDao> = mutableListOf()

            logger.info("Trying to load ${newBusStop.busNumbers.size} busNumbers from DB to replace them in this bus stop")
            for (busNumber: String in newBusStop.busNumbers) {
                // This is not atomic, and if some bus numbers exist, and some don't,
                // it will insert until it finds one that doesn't exist and stop there, which is crap
                if(this.busNumberRepository.existsByBusNumber(busNumber)) {
                    busNumberDaos.add(busNumberRepository.getByBusNumber(busNumber))
                }
                else {
                    throw Exception("Bus Number '$busNumber' doesn't exist")
                }
            }
            logger.info("Loaded ${busNumberDaos.size} bus numbers from DB")

            toBeUpdatedBusStopDao.changeOfficialHKBusStopId(newBusStop.officialHKBusStopId)
            toBeUpdatedBusStopDao.replaceBusNumbers(busNumberDaos)
            busStopRepository.save(toBeUpdatedBusStopDao)
            logger.info("Successfully updated ${newBusStop.busStopName} in DB")
            return toBeUpdatedBusStopDao.busStopId
        } else {
            logger.info("Creating a new bus stop with name ${newBusStop.busStopName}")
            val busNumberDaos: MutableList<BusNumberDao> = mutableListOf()
            logger.info("Trying to load ${newBusStop.busNumbers.size} busNumbers from DB to add them in this bus stop")
            for (busNumber: String in newBusStop.busNumbers) {
                // This is not atomic, and if some bus numbers exist, and some don't,
                // it will insert until it finds one that doesn't exist and stop there, which is crap
                if(this.busNumberRepository.existsByBusNumber(busNumber)) {
                    busNumberDaos.add(busNumberRepository.getByBusNumber(busNumber))
                }
                else {
                    throw Exception("Bus Number '$busNumber' doesn't exist")
                }
            }
            logger.info("Loaded ${busNumberDaos.size} bus numbers from DB")

            val insertedBusStopId: Long = busStopRepository.save(newBusStop.toDao(busNumberDaos)).busStopId
            logger.info("Successfully added ${newBusStop.busStopName} in DB")
            return insertedBusStopId
        }
    }

//    fun getBusStopByOfficialHKBusId(stopId: String): BusStop {
//        if (busStopRepository.existsByOfficialHKBusStopId(stopId)) {
//            return BusStop(this.busStopRepository.getByofficialHKBusStopId(stopId))
//        } else {
//            return BusStop(-1, "Unknown Bus Stop", mutableListOf(), "")
//        }
//    }

    fun getBusStopByBusStopId(stopId: Long): BusStop {
        return if (busStopRepository.existsById(stopId)) {
            BusStop(this.busStopRepository.findByIdOrNull(stopId)!!)
        } else {
            BusStop(-1, "Unknown Bus Stop", BusStopType.CTB, mutableListOf(), "")
        }
    }

    fun busStopExistsById(stopId: Long): Boolean {
        return this.busStopRepository.existsById(stopId)
    }

    fun getAllBusStopGroups() : MutableList<BusStopGroup>{
        val busStopGroupList = mutableListOf<BusStopGroup>()
        for (busStopGroupDao:BusStopGroupsDao in busStopGroupsRepository.findAll())
        {
            busStopGroupList.add(BusStopGroup(busStopGroupsDao = busStopGroupDao))
        }
        return busStopGroupList
    }

    fun groupExistsById(groupId: Long): Boolean {
        return this.busStopGroupsRepository.existsById(groupId)
    }

    fun getBusStopGroupById(groupId: Long): BusStopGroup {
        return if (busStopGroupsRepository.existsById(groupId)) {
            BusStopGroup(this.busStopGroupsRepository.findByIdOrNull(groupId)!!)
        } else {
            BusStopGroup(-1,"No group with id = $groupId")
        }
    }
}
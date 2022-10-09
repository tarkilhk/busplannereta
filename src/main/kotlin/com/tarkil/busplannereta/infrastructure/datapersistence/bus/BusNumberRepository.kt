package com.tarkil.busplannereta.infrastructure.datapersistence.bus

import org.springframework.data.repository.CrudRepository


interface BusNumberRepository : CrudRepository<BusNumberDao, Long> {
    fun getByBusNumber(busNumber: String): BusNumberDao
    fun existsByBusNumber(busNumber: String): Boolean

}
package com.tarkil.busplannereta.infrastructure.datapersistence.bus;

import org.springframework.data.jpa.repository.JpaRepository

interface BusStopGroupsRepository : JpaRepository<BusStopGroupsDao, Long> {

}
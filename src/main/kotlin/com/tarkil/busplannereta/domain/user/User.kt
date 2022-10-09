package com.tarkil.busplannereta.domain.user

import com.tarkil.busplannereta.domain.bus.BusStopGroup
import com.tarkil.busplannereta.infrastructure.datapersistence.users.UserDao

data class User (
    var dao : UserDao = UserDao(),
    var userId: Long = -1,
    var userName: String = "",
    var favouriteBusStopGroup: MutableList<BusStopGroup> = mutableListOf<BusStopGroup>())
{
    constructor(userDao: UserDao):this(
        userId = userDao.getUserId(), userName = userDao.name)
    {
        for (favouriteBusStopGroupDao in userDao.favouriteBusStopGroupsDaos)
        {
            this.favouriteBusStopGroup.add(BusStopGroup(busStopGroupsDao = favouriteBusStopGroupDao))
        }
    }
}
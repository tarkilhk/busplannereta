package com.tarkil.busplannereta.domain.user

import com.tarkil.busplannereta.domain.bus.BusStopGroup
import com.tarkil.busplannereta.infrastructure.datapersistence.bus.BusStopGroupsRepository
import com.tarkil.busplannereta.infrastructure.datapersistence.users.UserDao
import com.tarkil.busplannereta.infrastructure.datapersistence.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val busStopGroupsRepository: BusStopGroupsRepository) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun setFavouriteStopsFor(userId: Long, groupIds : MutableList<Long>) {
        if (userRepository.existsById(userId)) {
            val user: User = this.getUserById(userId)!!

            user.dao.favouriteBusStopGroupsDaos.clear()

            for(groupId:Long in groupIds) {
                if (busStopGroupsRepository.existsById(groupId)) {
                    user.dao.favouriteBusStopGroupsDaos.add(busStopGroupsRepository.findByIdOrNull(groupId)!!)
                }
                else {
                    // invalid busStopGroupId
                }
            }
            userRepository.save(user.dao)
        } else {
            // invalid user name
        }
    }

    fun getUserByName(name: String):User? {
        if (userRepository.existsByName(name)) {
            return(User(userRepository.findByName(name)))
        }
        return null
    }

    fun getFavouriteStopGroups(userId: Long): MutableList<BusStopGroup> {
        val myUser: User? = this.getUserById(userId)
        if (myUser == null) {
            return mutableListOf(BusStopGroup(-1, "Unknown user"))
        }
        else {
//            val busStopGroupList : MutableList<BusStopGroup> = mutableListOf()
//            for (busStopGroupDao in myUser.favouriteBusStopGroupsDaos) {
//                busStopGroupList.add(BusStopGroup(busStopGroupsDao = busStopGroupDao))
//            }
//            return(busStopGroupList)
            return myUser.favouriteBusStopGroup
        }
    }

    private fun getUserById(userId: Long): User? {
        if (userRepository.existsById(userId)) {
            return(User(userDao = userRepository.findById(userId).get()))
        }
        return null
    }

    fun getAll(): MutableList<User> {
        val userList : MutableList<User> = mutableListOf<User>()
        for (userDao:UserDao in userRepository.findAll())
        {
            userList.add(User(userDao))
        }
        return userList
    }
}
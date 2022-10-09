package com.tarkil.busplannereta.api.admin

import com.tarkil.busplannereta.domain.bus.BusStopGroup
import com.tarkil.busplannereta.domain.user.User
import com.tarkil.busplannereta.domain.user.UserService
import com.tarkil.busplannereta.infrastructure.datapersistence.users.UserDao
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/admin/users")
class UserController(val userService: UserService){

    @GetMapping("")
    fun getUsers() : MutableList<User>
    {
        return userService.getAll()
    }

    @GetMapping("favourite-groups")
    fun getFavouriteStopsForUser(@RequestParam(value="userId") userId: Long):MutableList<BusStopGroup>
    {
        return userService.getFavouriteStopGroups(userId = userId)
    }

    @PostMapping("favourite-groups")
    fun setFavouriteStopsForUser(@RequestParam(value="userId") userId: Long,
                                 @RequestParam(value="groupIDs") groupIds: MutableList<Long>):String
    {
        userService.setFavouriteStopsFor(userId, groupIds)
        return("Done")
    }
}
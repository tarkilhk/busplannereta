package com.tarkil.busplannereta.infrastructure.datapersistence.bus

import com.tarkil.busplannereta.infrastructure.datapersistence.users.UserDao
import javax.persistence.*

@Entity
@Table(name = "BUS_STOPS_GROUPS")
class BusStopGroupsDao(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val groupId: Long = -1,

    @Column(unique = true)
    val name: String = "",

    @OneToMany(mappedBy = "busStopGroup", cascade = arrayOf(CascadeType.ALL))
    val busStops : MutableList<BusStopDao> = mutableListOf<BusStopDao>(),

    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "favouriteBusStopGroupsDaos")
    val userDaos: MutableList<UserDao> = mutableListOf<UserDao>()
    )
{
    constructor(name: String) : this(
        groupId=-1,
        name = name
    )
}

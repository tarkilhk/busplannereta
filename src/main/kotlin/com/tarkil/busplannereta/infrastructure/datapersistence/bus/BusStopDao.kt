package com.tarkil.busplannereta.infrastructure.datapersistence.bus

import javax.persistence.*

enum class BusStopType {
        CTB, GMB
}

@Entity
@Table(name = "BUS_STOPS")
class BusStopDao (
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val busStopId: Long = -1,

        @Column(unique = true)
        val name: String,

        @Column
        var officialHKBusStopId: String,

        @Enumerated(EnumType.STRING)
        val type: BusStopType,

        @ManyToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinTable(
                name = "BUS_STOP_NUMBERS",
                joinColumns = arrayOf(JoinColumn(name = "bus_stop_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "bus_number_id"))
        )
        val busNumbers : MutableList<BusNumberDao> = mutableListOf<BusNumberDao>(),
//
//        @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "busStopDaos")
//        val busStopGroupDao: MutableList<BusStopGroupDao> = mutableListOf<BusStopGroupDao>()

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="groupId")
        val busStopGroup: BusStopGroupsDao = BusStopGroupsDao("")
)
{
//        constructor()
        constructor(busStopName: String, officialHKBusStopId: String, busStopType: BusStopType) : this(-1, busStopName, officialHKBusStopId, busStopType)
        constructor() : this(-1, "", "0", BusStopType.CTB)

//        override fun toString(): String {
//                return String.format(
//                        "Bus stop id=$busStopId : name $name - stop # $officialHKBusStopId [${type.toString()}]")
//        }

        fun replaceBusNumbers(busNumbersDaos: MutableList<BusNumberDao>) {
                this.busNumbers.clear()
                for(busNumerDao:BusNumberDao in busNumbersDaos) {
                        this.busNumbers.add(busNumerDao)
                }
        }

        fun changeOfficialHKBusStopId(officialHKBusStopId: String) {
                this.officialHKBusStopId = officialHKBusStopId
        }
}
package com.tarkil.busplannereta.domain.buseta

import java.time.ZonedDateTime

class BusETAItem (
        val data_timestamp : String,
        val eta : ZonedDateTime,
        val etaSequence : Int,
        val remark : String,
        val busNumber : String
)


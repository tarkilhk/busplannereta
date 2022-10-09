package com.tarkil.busplannereta.infrastructure.datagovhk

data class CityBusETADto(
        val `data`: List<CTBETAItemDto>,
//        val generated_timestamp: String,
        val type: String,
        val version: String
)

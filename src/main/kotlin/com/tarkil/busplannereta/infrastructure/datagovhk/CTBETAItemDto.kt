package com.tarkil.busplannereta.infrastructure.datagovhk

data class CTBETAItemDto(
    val co: String,
    val data_timestamp: String,
    val dest_en: String,
    val dest_sc: String,
    val dest_tc: String,
    val dir: String,
    val eta: String,
    val eta_seq: Int,
    val rmk_en: String,
    val rmk_sc: String,
    val rmk_tc: String,
    val route: String,
    val seq: Int,
    val stop: String
)
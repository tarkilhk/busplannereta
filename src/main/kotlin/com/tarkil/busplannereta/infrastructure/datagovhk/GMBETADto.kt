package com.tarkil.busplannereta.infrastructure.datagovhk

data class GMBETADto(
        val `data`: List<GMBETADataDto> = emptyList(),
        val generated_timestamp: String = "",
        val type: String = "",
        val version: String = ""
)
//{
//        constructor() : this(data= emptyList())
//}

data class GMBETADataDto(
        val route_id: Int = 0,
        val route_seq: Int = 0,
        val stop_seq: Int = 0,
        val enabled: String = "",
        val eta:List<GMBETADataItemDto> = emptyList()
)

data class GMBETADataItemDto(
        val eta_seq: Int = 0,
        val diff: Int = 0,
        val timestamp: String = "",
        val remarks_tc: String? = "",
        val remarks_sc: String? = "",
        val remarks_en: String? = ""
)
{
        val remarks:String
                get() = remarks_en?:""
}
package ir.omidtaheri.data.entity.searchLoacation



data class Feature(
    val geometry: Geometry,
    val place_name_en: String,
    val text_en: String
)
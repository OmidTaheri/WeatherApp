package ir.omidtaheri.domain.interactor.usecaseParam

data class GetForecastByCoordinatesParams(
    val lat: Double,
    val lon: Double,
    val units: String?,
    val cnt: Int?
)

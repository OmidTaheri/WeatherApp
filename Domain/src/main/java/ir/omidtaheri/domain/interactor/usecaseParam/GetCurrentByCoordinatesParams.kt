package ir.omidtaheri.domain.interactor.usecaseParam

data class GetCurrentByCoordinatesParams(
    val lat: Double,
    val lon: Double,
    val units: String?
)

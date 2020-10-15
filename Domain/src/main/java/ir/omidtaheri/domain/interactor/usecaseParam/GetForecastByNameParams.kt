package ir.omidtaheri.domain.interactor.usecaseParam

data class GetForecastByNameParams(
    val name: String,
    val units: String?,
    val cnt: Int?
)

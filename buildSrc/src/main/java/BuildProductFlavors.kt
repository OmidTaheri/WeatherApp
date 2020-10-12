import com.android.build.gradle.internal.dsl.ProductFlavor
import org.gradle.api.NamedDomainObjectContainer

interface BuildProductFlavor {
    val name: String

    fun libraryCreate(
        namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>
    ): ProductFlavor

    fun appCreate(
        namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>
    ): ProductFlavor
}

object FullFlavor : BuildProductFlavor {

    override val name = "full"

    override fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor {

        return namedDomainObjectContainer.create(name) {
            versionNameSuffix = "-full"
            dimension = BuildProductDimensions.BASEDIMENT
        }

    }

    override fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor {

        return namedDomainObjectContainer.create(name) {
            applicationIdSuffix = ".full"
            versionNameSuffix = "-full"
            dimension = BuildProductDimensions.BASEDIMENT
        }
    }

}


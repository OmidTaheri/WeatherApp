package extentions

import org.gradle.api.artifacts.dsl.RepositoryHandler

fun RepositoryHandler.applyDefault(){
    google()
    jcenter()
}
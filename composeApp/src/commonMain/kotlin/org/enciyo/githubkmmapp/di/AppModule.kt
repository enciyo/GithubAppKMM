package org.enciyo.githubkmmapp.di

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.enciyo.githubkmmapp.data.RemoteDataSource
import org.enciyo.githubkmmapp.ui.AppViewModel
import org.enciyo.githubkmmapp.ui.detail.DetailViewModel
import org.enciyo.githubkmmapp.ui.favorite.FavoriteViewModel
import org.enciyo.githubkmmapp.ui.home.HomeViewModel
import org.enciyo.githubkmmapp.ui.profile.ProfileViewModel
import org.enciyo.githubkmmapp.ui.search.SearchViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val ViewModelModules = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::DetailViewModel)
}

val NetworkModules = module {
    single { createHttpClient() }
    singleOf(::RemoteDataSource)
}

fun createHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
            prettyPrint = true
        })
    }
    install(Resources)
    install(Logging){
        this.level = LogLevel.ALL
    }
    defaultRequest {
        url("https://api.github.com/")
    }
}


package org.enciyo.githubkmmapp.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.enciyo.githubkmmapp.data.model.SearchResponse
import org.enciyo.githubkmmapp.data.resources.SearchResource


class RemoteDataSource(private val client: HttpClient) {


    suspend fun searchUser(q: String, page: Int = 1, perPage: Int = 30) = safeApiCall {
        if (q.isEmpty()) {
            return@safeApiCall SearchResponse(0, false, emptyList())
        }
        client.get(
            SearchResource.Users(
                q = q,
                page = page,
                per_page = perPage
            )
        ).body<SearchResponse>()
    }

    private suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.success(block.invoke())
        } catch (e: Exception) {
            when (e) {
                is ClientRequestException -> Result.failure(e)

                else -> Result.failure(e)
            }
        }
    }


}




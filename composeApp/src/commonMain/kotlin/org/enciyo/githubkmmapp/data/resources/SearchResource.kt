package org.enciyo.githubkmmapp.data.resources

import io.ktor.resources.*

@Resource("search")
class SearchResource {
    @Resource("users")
    class Users(
        val parent: SearchResource = SearchResource(),
        val q: String? = null,
        val page: Int? = null,
        val per_page: Int? = null
    )
}
package org.enciyo.githubkmmapp.data.resources

import io.ktor.resources.*

@Resource("users")
class UsersResource {
    @Resource("{username}")
    class Username(val parent: UsersResource = UsersResource(), val username: String)
}
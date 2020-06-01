package com.welmo.archhandson.step2.controller

import java.util.UUID

interface TaskClose {
    data class Request(
        val id: UUID
    )

    data class Response(
        val id: UUID
    )
}

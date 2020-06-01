package com.welmo.archhandson.step1.controller

import java.util.UUID

interface TaskClose {
    data class Request(
        val id: UUID
    )

    data class Response(
        val id: UUID
    )
}

package com.welmo.archhandson.step1.controller

import java.util.UUID

interface TaskCreate {
    data class Request(
        val title: String
    )

    data class Response(
        val id: UUID
    )
}

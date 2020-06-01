package com.welmo.archhandson.step1.controller

import java.util.UUID

interface TaskGet {
    data class Response(
        val id: UUID,
        val title: String,
        val status: String
    )
}

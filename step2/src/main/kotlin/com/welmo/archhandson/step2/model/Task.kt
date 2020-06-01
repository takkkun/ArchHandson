package com.welmo.archhandson.step2.model

import java.util.UUID

data class Task(
    val id: UUID,
    val title: String,
    val status: String
)

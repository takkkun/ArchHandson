package com.welmo.archhandson.step2.controller

import java.util.UUID

interface TaskList {
    data class Response(
        val tasks: List<Item>
    ) {

        data class Item(
            val id: UUID,
            val title: String,
            val status: String
        )
    }
}

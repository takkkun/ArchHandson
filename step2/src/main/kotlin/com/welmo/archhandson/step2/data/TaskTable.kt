package com.welmo.archhandson.step2.data

import org.jetbrains.exposed.sql.Table

object TaskTable : Table("task") {
    val id = uuid("id")

    val title = varchar("title", 50)

    val status = varchar("status", 10)

    override val primaryKey = PrimaryKey(id)
}

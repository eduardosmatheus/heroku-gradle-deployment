package org.elemental.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class SystemRepository(private val jdbcTemplate: NamedParameterJdbcTemplate? = null) {
    fun getConnectedDatabase(jdbc: JdbcTemplate): String? {
        val sql = "SELECT GLOBAL_NAME FROM GLOBAL_NAME"
        return jdbc.query(sql) {
            rs, _ -> rs.getString("GLOBAL_NAME")
        }.firstOrNull()
    }
}
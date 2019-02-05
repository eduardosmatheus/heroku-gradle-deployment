package org.elemental.databases

import com.zaxxer.hikari.HikariDataSource

object HikariCustomConfig {
    private val postgresqlTemplate: HikariDataSource

    init {
        postgresqlTemplate = postgreSqlTemplate()
    }

    fun getPostgresqlTemplate(): HikariDataSource = postgresqlTemplate

    private fun postgreSqlTemplate(): HikariDataSource {
        val hikariDS = HikariDataSource()
        hikariDS.jdbcUrl = System.getenv("SPRING_DATASOURCE_URL")
        return hikariDS
    }
}

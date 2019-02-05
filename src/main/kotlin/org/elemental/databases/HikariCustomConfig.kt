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
        hikariDS.dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        hikariDS.jdbcUrl = System.getenv("DATABASE_URL")
        return hikariDS
    }
}

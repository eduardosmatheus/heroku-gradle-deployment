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
        hikariDS.dataSourceClassName = "org.postgresql.ds.PGPoolingDataSource"
        hikariDS.addDataSourceProperty("portNumber", 32768)
        hikariDS.addDataSourceProperty("serverName", "172.18.0.1")
        hikariDS.addDataSourceProperty("user", "spring")
        hikariDS.addDataSourceProperty("password", "postgres")
        hikariDS.addDataSourceProperty("databaseName", "spring-postgres")
        return hikariDS
    }
}

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
        println(System.getenv("DATABASE_URL"))
        hikariDS.dataSourceClassName = "org.postgresql.ds.PGPoolingDataSource"
        hikariDS.jdbcUrl = "postgres://ctevovcnghkgof:80bb5353ee8ee495d86a9059b922bc07f9db96f6407d053e2bccd92f877e9df8@ec2-107-21-224-76.compute-1.amazonaws.com:5432/d2040b14djuqeh"
        return hikariDS
    }
}

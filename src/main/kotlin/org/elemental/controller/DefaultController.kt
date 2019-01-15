package org.elemental.controller

import org.elemental.databases.HikariCustomConfig
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
abstract class DefaultController {
    protected val postgresTemplate: NamedParameterJdbcTemplate =
        NamedParameterJdbcTemplate(HikariCustomConfig.getPostgresqlTemplate())
}
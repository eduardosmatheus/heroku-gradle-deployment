package org.elemental.repository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class UserRepository(private val jdbc: NamedParameterJdbcTemplate) {

    fun get(username: String, password: String) {

    }
}
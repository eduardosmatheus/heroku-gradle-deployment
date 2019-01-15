package org.elemental.security

import org.elemental.databases.HikariCustomConfig
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.*

@Component
class AuthProvider : AuthenticationProvider {

    protected val jdbcTemplate: JdbcTemplate = JdbcTemplate(HikariCustomConfig.getPostgresqlTemplate())

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val credentials = authentication.principal as AccountCredentials
        val password = credentials.password
        val sqlParameters = java.util.ArrayList<String>()
        sqlParameters.add(credentials.username)
        sqlParameters.add(password)

        val sql = "SELECT apelido from users where username = ? and password = ?"
        val claims = jdbcTemplate.query(sql, sqlParameters.toArray()) { rs: ResultSet, _ ->
            TokenClaims(rs.getString(1))
        }.firstOrNull()

        if(claims != null) {
            val tokenClaims = claims
            return UsernamePasswordAuthenticationToken(tokenClaims, password, ArrayList<GrantedAuthority>())
        } else {
            return null
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}
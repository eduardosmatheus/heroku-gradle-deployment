package org.elemental.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object TokenAuthenticationService {

    internal val EXPIRATION_TIME: Long = 860000000
    internal val SECRET = "MySecret"
    internal val TOKEN_PREFIX = "Bearer"
    internal val HEADER_STRING = "Authorization"

    internal fun addAuthentication(response: HttpServletResponse, claims: TokenClaims) {
        val JWT = Jwts.builder()
            .claim("username", claims)
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT)
        response.writer.write(TOKEN_PREFIX + " " + JWT)
    }

    internal fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)

        if (token != null) {
            val user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body["username"]

            if (user != null) {
                val data = user as Map<*, *>
                val tokenClaims = TokenClaims(data.get("username").toString())
                return UsernamePasswordAuthenticationToken(tokenClaims, null, emptyList<GrantedAuthority>())
            }
        }
        return null
    }

}
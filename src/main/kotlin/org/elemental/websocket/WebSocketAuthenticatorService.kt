package org.elemental.websocket

import io.jsonwebtoken.Jwts
import org.elemental.security.TokenAuthenticationService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component

@Component
class WebSocketAuthenticatorService {
    @Throws(AuthenticationException::class)
    fun getAuthenticatedOrFail(token: String): UsernamePasswordAuthenticationToken? {
        if (!token.isNullOrEmpty()) {
            val user = Jwts.parser()
                    .setSigningKey(TokenAuthenticationService.SECRET)
                    .parseClaimsJws(token.replace(TokenAuthenticationService.TOKEN_PREFIX, ""))
                    .body["user"]

            if (user != null) {
                val data = user as Map<*, *>
                return UsernamePasswordAuthenticationToken(data.get("cracha").toString(), null, AuthorityUtils.createAuthorityList("USER"))
            }
        }
        return null
    }
}
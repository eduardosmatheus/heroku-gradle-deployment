package org.elemental.rules

import org.elemental.security.TokenClaims
import org.springframework.security.core.Authentication

fun getClaims(authentication: Authentication): TokenClaims {
    return authentication.principal as TokenClaims
}
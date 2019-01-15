package org.elemental.controller

import org.elemental.security.TokenClaims
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController: DefaultController() {

    @GetMapping("/currentUser")
    fun getCurrentUser(authentication: Authentication): TokenClaims {
        return authentication.principal as TokenClaims
    }

//    @GetMapping("/{codUser}/apelido")
//    fun getApelido(@PathVariable("codUser") codUser: Int): String? {
//        return UserRepository(oracleTemplate).getApelido(codUser)
//    }
}
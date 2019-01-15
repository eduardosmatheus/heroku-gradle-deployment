package org.elemental.security

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountCredentials(@JsonProperty("username") val username: String,
                              @JsonProperty("password") val password: String)
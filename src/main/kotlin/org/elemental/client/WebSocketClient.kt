package org.elemental.client

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

class WebSocketClient {
    val BASE_URL = ""
    val SEND_MESSAGE = "${BASE_URL}/send"

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class TextData(val userName: String, val text: String, val link: String, val id: String, val type: String)

    fun sendMessage(userName: String, text: String, link: String, id: String, type: String) {
        val payload = TextData(userName, text, link, id, type)
        val headers = HttpHeaders()
        val entity = HttpEntity(payload, headers)
        val restTemplate = RestTemplate()
        restTemplate.exchange<String>(SEND_MESSAGE, HttpMethod.POST, entity, String::class.java)
    }
}

package org.elemental.websocket

import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import javax.naming.AuthenticationException
import org.springframework.messaging.Message

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AuthChannelInterceptorAdapter(private val webSocketAuthenticatorService: WebSocketAuthenticatorService) : ChannelInterceptor {
    @Throws(AuthenticationException::class)
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        if (StompCommand.CONNECT == accessor.command) {
            val token = accessor.getFirstNativeHeader(AUTHORIZATION)
            val user = webSocketAuthenticatorService.getAuthenticatedOrFail(token)
            accessor.user = user
        }
        return message
    }

    companion object {
        private val AUTHORIZATION = "authorization"
    }
}
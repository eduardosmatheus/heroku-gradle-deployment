package org.elemental.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import javax.inject.Inject
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class WebSocketAuthenticationSecurityConfig : WebSocketMessageBrokerConfigurer {
    @Inject
    private val webSocketAuthenticatorService: WebSocketAuthenticatorService? = null

    override fun registerStompEndpoints(registry: StompEndpointRegistry?) {
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration?) {
        registration!!.interceptors(AuthChannelInterceptorAdapter(this.webSocketAuthenticatorService!!))
    }

}
package com.tuantran.IMPROOK_CARE.configs.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @SuppressWarnings("null")
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/public/webSocket/").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/api/public/notification/").setAllowedOriginPatterns("*").withSockJS();
    }

    @SuppressWarnings("null")
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/chatroom", "/user");
        registry.setUserDestinationPrefix("/user");
    }
}

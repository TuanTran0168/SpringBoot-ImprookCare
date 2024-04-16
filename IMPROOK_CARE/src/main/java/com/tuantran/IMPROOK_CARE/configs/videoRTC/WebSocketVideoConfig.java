package com.tuantran.IMPROOK_CARE.configs.videoRTC;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableWebSocket
public class WebSocketVideoConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(videoChatHandler(), "/api/public/video-chat/{roomId}").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler videoChatHandler() {
        return new VideoChatConfig();
    }
}

// class VideoChatHandler extends TextWebSocketHandler {

// private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
// private final AtomicBoolean isPolitePeer = new AtomicBoolean(true);

// @Override
// public void afterConnectionEstablished(WebSocketSession session) throws
// Exception {
// String jsonResponse = "{\"polite\": \"" +
// isPolitePeer.getAndSet(!isPolitePeer.get()) + "\"}";
// session.sendMessage(new TextMessage(jsonResponse));
// sessions.add(session);
// System.out.println("-----------------------------------------------------------------------------");
// System.out.println("Connection opened! " + jsonResponse);
// System.out.println("-----------------------------------------------------------------------------");
// }

// @Override
// protected void handleTextMessage(WebSocketSession session, TextMessage
// message) throws Exception {
// for (WebSocketSession s : sessions) {
// if (!s.getId().equals(session.getId())) {
// s.sendMessage(message);
// System.out.println("-----------------------------------------------------------------------------");
// System.out.println(message);
// System.out.println("-----------------------------------------------------------------------------");
// }
// }
// }

// @Override
// public void afterConnectionClosed(WebSocketSession session, CloseStatus
// status) throws Exception {
// System.out.println("INSIDE CLOSING STATE");
// System.out.println("-----------------------------------------------------------------------------");
// System.out.println("Connection Closed! " + status);
// System.out.println("-----------------------------------------------------------------------------");
// sessions.removeIf(s -> s.getId().equals(session.getId()));
// }
// }

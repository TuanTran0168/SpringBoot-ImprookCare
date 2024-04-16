package com.tuantran.IMPROOK_CARE.configs.videoRTC;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@EnableWebSocket
public class VideoChatConfig extends TextWebSocketHandler {

    private final Map<String, List<WebSocketSession>> sessionsByRoomId = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = extractRoomIdFromSession(session);
        addUserToRoom(roomId, session);
        System.out.println("Connection opened! Session ID: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = extractRoomIdFromSession(session);
        List<WebSocketSession> usersInRoom = getUsersInRoom(roomId);
        for (WebSocketSession userSession : usersInRoom) {
            if (!userSession.getId().equals(session.getId())) {
                userSession.sendMessage(message);
            }
        }
        System.out.println(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = extractRoomIdFromSession(session);
        removeUserFromRoom(roomId, session);
        System.out.println("Connection Closed! Session ID: " + session.getId());
    }

    private synchronized void addUserToRoom(String roomId, WebSocketSession session) {
        List<WebSocketSession> sessions = sessionsByRoomId.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<>());
        sessions.add(session);
    }

    private synchronized void removeUserFromRoom(String roomId, WebSocketSession session) {
        List<WebSocketSession> sessions = sessionsByRoomId.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                sessionsByRoomId.remove(roomId);
            }
        }
    }

    private synchronized List<WebSocketSession> getUsersInRoom(String roomId) {
        return sessionsByRoomId.getOrDefault(roomId, Collections.emptyList());
    }

    private String extractRoomIdFromSession(WebSocketSession session) {
        String uri = session.getUri().toString();
        // Extract roomId from URI
        // Example: ws://localhost:8080/api/public/video-chat/abcdef
        // The roomId would be "abcdef"
        String[] parts = uri.split("/");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return null;
    }
}
// public class VideoChatConfig extends TextWebSocketHandler {

// @Override
// public void afterConnectionEstablished(WebSocketSession session) throws
// Exception {
// String roomId = extractRoomIdFromSession(session);
// RoomManager.addUserToRoom(roomId, session);
// System.out.println("-----------------------------------------------------------------------------");
// System.out.println("Connection opened! " + session);
// System.out.println("-----------------------------------------------------------------------------");
// }

// @Override
// protected void handleTextMessage(WebSocketSession session, TextMessage
// message) throws Exception {
// String roomId = extractRoomIdFromSession(session);
// List<WebSocketSession> usersInRoom = RoomManager.getUsersInRoom(roomId);
// for (WebSocketSession userSession : usersInRoom) {
// if (!userSession.getId().equals(session.getId())) {
// userSession.sendMessage(message);
// }
// }

// System.out.println("-----------------------------------------------------------------------------");
// System.out.println(message);
// System.out.println("-----------------------------------------------------------------------------");
// }

// @Override
// public void afterConnectionClosed(WebSocketSession session, CloseStatus
// status) throws Exception {
// String roomId = extractRoomIdFromSession(session);
// RoomManager.removeUserFromRoom(roomId, session);
// System.out.println("-----------------------------------------------------------------------------");
// System.out.println("Connection Closed! " + status);
// System.out.println("-----------------------------------------------------------------------------");
// }

// private String extractRoomIdFromSession(WebSocketSession session) {
// String uri = session.getUri().toString();
// // Lấy roomId từ URI
// // Ví dụ: ws://localhost:8080/api/public/video-chat/abcdef
// // roomId sẽ là "abcdef"
// String[] parts = uri.split("/");
// if (parts.length > 0) {
// return parts[parts.length - 1];
// }
// return null;
// }
// }

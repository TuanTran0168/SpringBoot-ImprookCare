package com.tuantran.IMPROOK_CARE.configs.videoRTC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.WebSocketSession;

public class RoomManager {
    private static final Map<String, List<WebSocketSession>> rooms = new HashMap<>();

    public static void addUserToRoom(String roomId, WebSocketSession session) {
        rooms.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    public static void removeUserFromRoom(String roomId, WebSocketSession session) {
        rooms.getOrDefault(roomId, new CopyOnWriteArrayList<>()).remove(session);
    }

    public static List<WebSocketSession> getUsersInRoom(String roomId) {
        return rooms.getOrDefault(roomId, new CopyOnWriteArrayList<>());
    }
}

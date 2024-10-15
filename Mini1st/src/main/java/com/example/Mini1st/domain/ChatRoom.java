package com.example.Mini1st.domain;


import com.example.Mini1st.service.ChatService;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom implements Serializable {
    // 채팅방 개설하고 채팅방 정보를 저장하는 클래스.

    private int roomId; // 시퀀스 번호
    private String room_name; // 채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom(String roomName) {

    }
}
package com.example.Mini1st.service;

import com.example.Mini1st.dao.ChatMessageRepository;
import com.example.Mini1st.dao.ChatRoomRepository;
import com.example.Mini1st.domain.ChatMessage;
import com.example.Mini1st.domain.ChatRoom;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Service
@ServerEndpoint(value = "/chat")
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        return chatRoomRepository.findAllRoom();
    }
    // 채팅방 조회, 생성
    public ChatRoom getOrCreateRoom(int roomId) {
        // 채팅방을 검색
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        if (chatRoom == null) {
            // 채팅방이 없으면 새로 생성
            chatRoom = new ChatRoom();
            chatRoom.setRoom_name("New Room: " + roomId);
            chatRoomRepository.save(chatRoom); // 자동 증가 ID 할당
        }
        return chatRoom;
    }

    public void createChatRoom(ChatRoom chatRoom) {
        if (chatRoom.getRoom_name() == null || chatRoom.getRoom_name().trim().isEmpty()) {
            throw new IllegalArgumentException("채팅방 이름은 비어 있을 수 없습니다.");
        }
        chatRoomRepository.save(chatRoom);
    }

    public ChatRoom getChatRoomById(int roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    public List<ChatMessage> getMessagesByRoomId(int roomId) {
        return chatMessageRepository.findMessagesByRoomId(roomId);
    }

    // 채팅
    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }
}

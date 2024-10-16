package com.example.Mini1st.service;

import com.example.Mini1st.dao.ChatMessageRepository;
import com.example.Mini1st.dao.ChatRoomRepository;
import com.example.Mini1st.domain.ChatMessage;
import com.example.Mini1st.domain.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final RedisTemplate<String, Object> redisTemplate;  // 중복된 필드 제거
    private final ObjectMapper objectMapper;

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

    // 새로운 채팅방 생성
    public void createChatRoom(ChatRoom chatRoom) {
        if (chatRoom.getRoom_name() == null || chatRoom.getRoom_name().trim().isEmpty()) {
            throw new IllegalArgumentException("채팅방 이름은 비어 있을 수 없습니다.");
        }
        chatRoomRepository.save(chatRoom);
    }

    // 채팅방 ID로 조회
    public ChatRoom getChatRoomById(int roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    // 메시지 저장 메서드
    public void saveMessageToRedis(ChatMessage message) {
        String key = "chatRoom:" + message.getRoomId() + ":messages";
        redisTemplate.opsForList().rightPush(key, message);
    }

    // 채팅방 ID로 메시지 조회
    public List<ChatMessage> getMessagesByRoomId(int roomId) {
        String key = "chatRoom:" + roomId + ":messages";
        List<Object> messages = redisTemplate.opsForList().range(key, 0, -1);

        // 역직렬화를 통해 List<Object>를 List<ChatMessage>로 변환
        List<ChatMessage> chatMessages = new ArrayList<>();
        if (messages != null) {
            for (Object obj : messages) {
                ChatMessage chatMessage = objectMapper.convertValue(obj, ChatMessage.class);
                chatMessages.add(chatMessage);
            }
        }
        return chatMessages;
    }
}

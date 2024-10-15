package com.example.Mini1st.service;

import com.example.Mini1st.dao.ChatMessageRepository;
import com.example.Mini1st.dao.ChatRoomRepository;
import com.example.Mini1st.domain.ChatMessage;
import com.example.Mini1st.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final SimpMessagingTemplate messagingTemplate;

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

    // 채팅방 ID로 메시지 조회
    public List<ChatMessage> getMessagesByRoomId(int roomId) {
        return chatMessageRepository.findMessagesByRoomId(roomId);
    }

//    // 메시지를 Redis에 저장
//    public void saveMessageToRedis(ChatMessage message) {
//        try {
//            String messageJson = objectMapper.writeValueAsString(message);  // ChatMessage 객체를 JSON 형식으로 변환
//            redisPublisher.publish(topic, messageJson);  // RedisPublisher를 사용해 메시지를 퍼블리시
//        } catch (Exception e) {
//            System.err.println("메시지 변환 중 에러 발생: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
// 웹소켓을 통해 메시지 브로드캐스트
    public void sendMessage(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/chatRoom/" + message.getRoomId(), message);
    }

}

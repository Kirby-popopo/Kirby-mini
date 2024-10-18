package com.example.Mini1st.service;

import com.example.Mini1st.dao.ChatMessageRepository;
import com.example.Mini1st.dao.ChatRoomRepository;
import com.example.Mini1st.domain.ChatMessage;
import com.example.Mini1st.domain.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final RedisTemplate<String, Object> redisTemplate;
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

    // getMessagesByRoomId: 채팅 메시지를 조회하고, 날짜 포맷을 설정하여 displayDate를 활용해 뷰에서 날짜 또는 시간을 출력.
    public List<ChatMessage> getMessagesByRoomId(int roomId) {
        String key = "chatRoom:" + roomId + ":messages";
        List<Object> messages = redisTemplate.opsForList().range(key, 0, -1);

        List<ChatMessage> chatMessages = new ArrayList<>();
        String previousDate = "";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm").withLocale(Locale.KOREA);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:mm").withLocale(Locale.KOREA);

        if (messages != null) {
            for (Object obj : messages) {
                ChatMessage chatMessage = objectMapper.convertValue(obj, ChatMessage.class);

                // sendDate를 KST로 변환하여 포맷팅
                chatMessage.setSendDate(chatMessage.getSendDate().withOffsetSameInstant(ZoneOffset.ofHours(9)));

                String currentDate = chatMessage.getSendDate().toLocalDate().toString();
                if (!currentDate.equals(previousDate)) {
                    chatMessage.setDisplayDate(chatMessage.getSendDate().format(dateFormatter));
                    previousDate = currentDate;
                } else {
                    chatMessage.setDisplayDate(null); // 같은 날이면 표시할 날짜 없음
                }

                chatMessages.add(chatMessage);
            }
        }
        return chatMessages;
    }

    // 메시지 조회 시, 포맷된 날짜를 formattedSendDate 필드에 저장하여 뷰에서 사용.
    public List<ChatMessage> getMessagesWithFormattedDatesByRoomId(int roomId) {
        String key = "chatRoom:" + roomId + ":messages";
        List<Object> messages = redisTemplate.opsForList().range(key, 0, -1);

        List<ChatMessage> chatMessages = new ArrayList<>();
        String previousDate = "";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm").withLocale(Locale.KOREA);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:mm").withLocale(Locale.KOREA);

        if (messages != null) {
            for (Object obj : messages) {
                ChatMessage chatMessage = objectMapper.convertValue(obj, ChatMessage.class);

                String currentDate = chatMessage.getSendDate().toLocalDate().toString();
                String formattedSendDate;
                if (!currentDate.equals(previousDate)) {
                    formattedSendDate = chatMessage.getSendDate().format(dateFormatter);
                    previousDate = currentDate;
                } else {
                    formattedSendDate = chatMessage.getSendDate().format(timeFormatter);
                }

                chatMessage.setFormattedSendDate(formattedSendDate);
                chatMessages.add(chatMessage);
            }
        }
        return chatMessages;
    }


}

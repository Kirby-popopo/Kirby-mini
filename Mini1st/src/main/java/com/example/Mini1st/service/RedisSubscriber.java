package com.example.Mini1st.service;

import com.example.Mini1st.domain.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSubscriber {
    // JSON 형태로 수신된 Redis 메시지를 Java 객체로 변환하는데 사용.
    private final ObjectMapper objectMapper;
    // 웹 소켓 메시지 전송 인터페이스, 특정 topic 으로 메시지 브로드캐스트 하는데에 사용
    private final SimpMessageSendingOperations messagingTemplate;

    // Redis 채널에서 수신된 메시지 처리
    public void onMessage(String message) {
        try {
            // Redis에서 수신한 메시지를 ChatMessage 객체로 변환
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
            System.out.println("Redis로부터 수신한 메시지 : " + message);

            // 웹소켓 구독자들에게 채팅 메시지 전송
            messagingTemplate.convertAndSend("/room/chatRoom/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            System.out.println("메시지 처리중 에러 발생 : " + e.getMessage());
        }
    }
}

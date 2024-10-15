package com.example.Mini1st.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private MessageType type; // 메시지 타입
    private String message; // 메시지
    private LocalDateTime sendDate; // 발송 날짜
    private int roomId; // 발송지인 채팅방 (FK), 방 번호
    private String sender; // 발송자
}


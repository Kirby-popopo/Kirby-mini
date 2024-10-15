package com.example.Mini1st.domain;

import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private MessageType type; // 메시지 타입
    private String message; // 메시지
    private LocalDateTime sendDate; // 발송 날짜
    private int roomId; // 발송지인 채팅방 (FK), 방 번호
    private String sender; // 발송자
}

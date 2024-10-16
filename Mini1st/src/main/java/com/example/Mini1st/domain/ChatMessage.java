package com.example.Mini1st.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {

    private MessageType type; // 메시지 타입
    private String message; // 메시지


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private OffsetDateTime sendDate; // 시간대 정보를 포함한 OffsetDateTime으로 변경

    private int roomId; // 발송지인 채팅방 (FK), 방 번호
    private String sender; // 발송자
}

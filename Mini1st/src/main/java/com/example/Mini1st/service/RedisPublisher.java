package com.example.Mini1st.service;

import com.example.Mini1st.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChatMessage message) {
        redisTemplate.convertAndSend("chatroom", message);
    }

}

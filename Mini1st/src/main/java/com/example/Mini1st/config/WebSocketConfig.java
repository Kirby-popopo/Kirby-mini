package com.example.Mini1st.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 메시지 브로커 - 중간에서 메시지를 처리해주는 중개역할

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 간단한 메모리 기반 메시지 브로커를 활성화하고, 해당 브로커의 목적지 접두사를 "/room" 로 설정.
        // 해당 주소를 구독하고 있는 클라이언트 들에게 메시지 전달.
        registry.enableSimpleBroker("/topic");  // 메시지를 구독하는 경로 prefix
        // 애플리케이션에서 처리할 메시지의 접두사를 "/room" 으로 설정.
        // 클라이언트에서 보낸 메시지를 받을 prefix
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP 엔드 포인트를 등록하는 메서드
        // 클라이언트가 웹소켓 서버에 연결할 수 있는 엔드 포인트 설정.
        // "/ws-stomp" 엔드 포인트 추가하고, 모든 도메인에서의 요청을 허용, SockJS 지원 활성화.
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
    }
}

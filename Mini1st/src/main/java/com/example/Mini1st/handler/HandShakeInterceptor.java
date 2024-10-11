package com.example.Mini1st.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class HandShakeInterceptor implements HandshakeInterceptor {

    // 웹소켓 연결 전 인터셉터
    // Authorization 헤더를 확인하여 유저를 인증한다.
    // 유저 명이 채팅유저로 시작하지 않으면 401 반환
    // 유저 명이 채팅유저로 시작해서 인증된 유저라면 session에 username을 저장.

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

            String user_name = servletRequest.getHeader("Authorization");
            if (user_name != null && user_name.startsWith("chatUser")) { // chatUser로 시작하지 않으면 권한없음으로 반려. / 추후 요구사항에 따라 수정 ex) JWT토큰 또는 세션기반 등등
                attributes.put("user_name", user_name);
                return true;
            } else {
                servletResponse.setStatus(401);
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

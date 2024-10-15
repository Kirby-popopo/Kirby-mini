package com.example.Mini1st.controller;

import com.example.Mini1st.domain.ChatMessage;
import com.example.Mini1st.domain.ChatRoom;
import com.example.Mini1st.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ChatController {
    @Autowired
    ChatService chatService;

    private SimpMessagingTemplate messagingTemplate;

    // 채팅방 리스트 조회
    @GetMapping("/roomList")
    public String roomList(Model model) {
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList", roomList);
        return "chat/roomList";
    }

    // 채팅방 생성 또는 조회 화면
    @GetMapping("/createRoom")
    public String getOrCreateRoom(@RequestParam int roomId, Model model) {
        ChatRoom chatRoom = chatService.getOrCreateRoom(roomId);
        model.addAttribute("chatRoom", chatRoom);
        return "chat/createRoom"; // 채팅방을 조회하거나 새로 생성된 후 뷰로 리턴
    }

    // 새로운 채팅방 생성
    @PostMapping("/roomAdd")
    public String createRoom(@RequestParam String room_name) {
        if (room_name == null || room_name.trim().isEmpty()) {
            throw new IllegalArgumentException("채팅방 이름은 필수입니다.");
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoom_name(room_name);
        chatService.createChatRoom(chatRoom);
        return "redirect:/roomList"; // 새로운 채팅방 생성 후 채팅방 목록 페이지로 리다이렉트
    }

    // 채팅방 입장
    @GetMapping("/chatRoom/{roomId}")
    public String enterRoom(@PathVariable int roomId, Model model) {
        ChatRoom chatRoom = chatService.getChatRoomById(roomId);
        List<ChatMessage> messages = chatService.getMessagesByRoomId(roomId);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("messages", messages);
        return "chat/chatRoom"; // 채팅방 뷰로 이동
    }
    
    // 채팅 전송
    @MessageMapping("/chat/message") // 클라이언트에서 보낸 메시지를 수신하는 엔드포인트
    @SendTo("/room/chatRoom/{roomId}") // 해당 방에 있는 모든 클라이언트로 메시지를 전송
    public ChatMessage sendMessage(ChatMessage message) {
        // 메시지를 Redis에 저장하는 로직 추가
        chatService.saveMessage(message);
        return message;
    }

}

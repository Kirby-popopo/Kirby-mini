package com.example.Mini1st.controller;

import com.example.Mini1st.domain.ChatMessage;
import com.example.Mini1st.domain.ChatRoom;
import com.example.Mini1st.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    private ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

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
        return "chat/createRoom";
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
        return "redirect:/roomList";
    }

    // 채팅방 입장
    @GetMapping("/chatRoom/{roomId}")
    public String enterRoom(@PathVariable int roomId, Model model) {
        ChatRoom chatRoom = chatService.getChatRoomById(roomId);
        List<ChatMessage> messages = chatService.getMessagesByRoomId(roomId);

        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("messages", messages);
        return "chat/chatRoom";
    }

    // 채팅 메시지 전송 처리
    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessage message) {
        // 메시지를 해당 채팅방에 브로드캐스트
        messagingTemplate.convertAndSend("/topic/chatRoom/" + message.getRoomId(), message);

        // Redis에 메시지를 저장하기 위해 서비스 호출
        chatService.saveMessageToRedis(message);
    }
}

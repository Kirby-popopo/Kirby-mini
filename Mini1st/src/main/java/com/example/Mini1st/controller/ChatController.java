package com.example.Mini1st.controller;

import com.example.Mini1st.dao.login.UserDTO;
import com.example.Mini1st.domain.ChatMessage;
import com.example.Mini1st.domain.ChatRoom;
import com.example.Mini1st.service.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 채팅방 리스트 조회
     *
     * @param model 모델 객체에 채팅방 리스트를 추가
     * @return 채팅방 리스트 화면 (HTML 페이지)
     */
    @GetMapping("/roomList")
    public String roomList(Model model, HttpSession session) {
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList", roomList);

        UserDTO loginMember = (UserDTO) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/login"; // 세션에 로그인 정보가 없으면 로그인 페이지로 이동
        }

        // 특정 채팅방 ID로 chatRoom 객체를 가져옵니다.
        if (!roomList.isEmpty()) {
            ChatRoom chatRoom = chatService.getChatRoomById(roomList.get(0).getRoomId());
            model.addAttribute("chatRoom", chatRoom);
        }

        return "chat/roomList";
    }

    /**
     * 채팅방 생성 또는 조회 화면
     *
     * @param roomId 채팅방 ID
     * @param model  모델 객체에 채팅방 정보를 추가
     * @return 채팅방 생성 또는 조회 화면 (HTML 페이지)
     */
    @GetMapping("/createRoom")
    public String getOrCreateRoom(@RequestParam int roomId, Model model) {
        ChatRoom chatRoom = chatService.getOrCreateRoom(roomId);
        model.addAttribute("chatRoom", chatRoom);
        return "chat/createRoom";
    }

    /**
     * 새로운 채팅방 생성
     *
     * @param room_name 채팅방 이름
     * @return 채팅방 리스트 페이지로 리다이렉트
     */
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

    /**
     * 채팅방 입장
     *
     * @param roomId 채팅방 ID
     * @param model  모델 객체에 채팅방 정보와 최근 메시지를 추가
     * @return 채팅방 화면 (HTML 페이지)
     */
    @GetMapping("/chatRoom/{roomId}")
    public String enterRoom(@PathVariable int roomId, Model model, HttpSession session) {
        ChatRoom chatRoom = chatService.getChatRoomById(roomId);
        List<ChatMessage> messages = chatService.getRecentMessagesByRoomId(roomId); // 최근 메시지만 로드

        UserDTO loginMember = (UserDTO) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/login"; // 세션에 로그인 정보가 없으면 로그인 페이지로 이동
        }
        String name = loginMember.getName();

        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("messages", messages);
        model.addAttribute("name", name);

        System.out.println("채팅방 입장 : "+ name);

        return "chat/chatRoom";
    }

    /**
     * 채팅 메시지 전송 처리
     *
     * @param message 전송된 채팅 메시지 객체
     */
    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessage message) {
        // 메시지를 해당 채팅방에 브로드캐스트
        messagingTemplate.convertAndSend("/topic/chatRoom/" + message.getRoomId(), message);

        // Redis에 메시지를 저장하기 위해 서비스 호출
        chatService.saveMessageToRedis(message);
    }

    /**
     * 이전 메시지 조회 API
     *
     * @param roomId 채팅방 ID
     * @param before 조회 기준이 되는 날짜 (ISO-8601 형식)
     * @param limit  가져올 메시지의 최대 개수
     * @return 이전 메시지 리스트 (JSON 형태)
     */
    @GetMapping("/chat/messages/previous")
    public ResponseEntity<List<ChatMessage>> getPreviousMessages(
            @RequestParam int roomId, @RequestParam String before, @RequestParam(defaultValue = "20") int limit) {
        OffsetDateTime beforeDate = OffsetDateTime.parse(before);
        List<ChatMessage> messages = chatService.getMessagesBefore(roomId, beforeDate, limit);
        return ResponseEntity.ok(messages);
    }

    /**
     * 모든 채팅방을 JSON 형태로 반환하는 API
     *
     * @return 채팅방 리스트 (JSON 형태)
     */
    @GetMapping("/chat/rooms")
    public ResponseEntity<List<ChatRoom>> getAllChatRooms() {
        List<ChatRoom> roomList = chatService.findAllRoom();
        return ResponseEntity.ok(roomList);
    }
}

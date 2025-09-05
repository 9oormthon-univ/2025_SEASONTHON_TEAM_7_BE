package goormthonuniv.team_7_be.api.chat.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import goormthonuniv.team_7_be.api.chat.dto.request.ChatMessageRequest;
import goormthonuniv.team_7_be.api.chat.dto.request.ChatReadRequest;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatMessageResponse;
import goormthonuniv.team_7_be.api.chat.service.ChatMessageService;
import goormthonuniv.team_7_be.common.auth.resolver.Auth;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageRequest request, Principal principal) {
        String username = principal.getName();
        ChatMessageResponse response = chatMessageService.send(request, username);

        // 특정 채팅방 구독자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat-room/" + request.chatRoomId(), ApiResponse.success(response));
    }

    @MessageMapping("/chat/read")
    public void readMessage(ChatReadRequest request, @Auth Long memberId) {
        chatMessageService.readMessages(request.chatRoomId(), memberId);
    }

    /**
     * 채팅 테스트용 페이지
     */
    @GetMapping("/chat-test")
    public String chatTestPage() {
        return "chat-test";
    }
}

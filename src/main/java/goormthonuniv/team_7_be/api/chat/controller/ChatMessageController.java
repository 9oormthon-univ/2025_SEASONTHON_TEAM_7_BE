package goormthonuniv.team_7_be.api.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import goormthonuniv.team_7_be.api.chat.dto.request.ChatMessageRequest;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatMessageResponse;
import goormthonuniv.team_7_be.api.chat.service.ChatMessageService;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send") // 클라이언트에서 /pub/chat.send로 메시지 전송
    public void sendMessage(
        // @Parameter(hidden = true) @Auth Long memberId,
        ChatMessageRequest request
    ) {
        log.info("Received message: {}", request);
        ChatMessageResponse response = chatMessageService.send(1L, request);

        // 특정 채팅방 구독자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat-room/" + request.chatRoomId(), ApiResponse.success(response));
    }

    /**
     * 채팅 테스트용 페이지
     */
    @GetMapping("/chat-test")
    public String chatTestPage() {
        return "chat-test";
    }
}

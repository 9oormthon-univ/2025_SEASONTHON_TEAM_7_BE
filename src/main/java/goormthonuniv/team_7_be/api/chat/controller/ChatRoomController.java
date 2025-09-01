package goormthonuniv.team_7_be.api.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goormthonuniv.team_7_be.api.chat.dto.request.ChatRoomCreateRequest;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatMessageResponse;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatRoomResponse;
import goormthonuniv.team_7_be.api.chat.service.ChatRoomService;
import goormthonuniv.team_7_be.common.auth.resolver.Auth;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "ChatRoom", description = "채팅방 API")
@RestController
@RequestMapping("/v1/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "[인증 필요] 채팅방 생성", description = "새로운 채팅방을 생성합니다.")
    @PostMapping
    public ApiResponse<ChatRoomResponse> createChatRoom(
        @Parameter(hidden = true) @Auth Long memberId,
        @RequestBody ChatRoomCreateRequest request
    ) {
        ChatRoomResponse response = chatRoomService.createChatRoom(memberId, request);
        return ApiResponse.success(response);
    }

    @Operation(summary = "[인증 필요] 내 채팅방 목록 조회", description = "내가 참여한 모든 채팅방을 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<List<ChatRoomResponse>> getMyChatRooms(@Parameter(hidden = true) @Auth Long memberId) {
        List<ChatRoomResponse> responses = chatRoomService.getMyChatRooms(memberId);
        return ApiResponse.success(responses);
    }

    @Operation(summary = "[인증 필요] 채팅방 메시지 조회", description = "특정 채팅방의 모든 메시지를 조회합니다.")
    @GetMapping("/{chatRoomId}/messages")
    public ApiResponse<List<ChatMessageResponse>> getChatMessages(
        @Parameter(hidden = true) @Auth Long memberId,
        @PathVariable Long chatRoomId
    ) {
        List<ChatMessageResponse> responses = chatRoomService.getChatMessages(chatRoomId, memberId);
        return ApiResponse.success(responses);
    }
}
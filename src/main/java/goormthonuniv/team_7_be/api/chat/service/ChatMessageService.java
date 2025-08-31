package goormthonuniv.team_7_be.api.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goormthonuniv.team_7_be.api.chat.dto.request.ChatMessageRequest;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatMessageResponse;
import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;
import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;
import goormthonuniv.team_7_be.api.chat.exception.ChatExceptionType;
import goormthonuniv.team_7_be.api.chat.repository.ChatMessageRepository;
import goormthonuniv.team_7_be.api.chat.repository.ChatRoomRepository;
import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 메시지 전송
    public ChatMessageResponse send(Long senderId, ChatMessageRequest request) {
        Member member = memberRepository.findById(senderId)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(request.chatRoomId())
            .orElseThrow(() -> new BaseException(ChatExceptionType.CHAT_ROOM_NOT_FOUND));

        ChatMessage chatMessage = ChatMessage.builder()
            .chatRoom(chatRoom)
            .sender(member)
            .content(request.content())
            .messageType(request.messageType())
            .messageContentType(request.contentType())
            .build();

        chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.from(chatMessage);
    }

    // 메시지 저장
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    // 특정 채팅방의 모든 메시지 조회
    @Transactional(readOnly = true)
    public List<ChatMessage> getMessageByRoomId(Long roomId) {
        return chatMessageRepository.findByChatRoomIdOrderByCreatedAtAsc(roomId);
    }
}

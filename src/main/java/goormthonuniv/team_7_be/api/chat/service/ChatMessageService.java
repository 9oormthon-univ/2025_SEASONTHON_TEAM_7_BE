package goormthonuniv.team_7_be.api.chat.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goormthonuniv.team_7_be.api.chat.dto.request.ChatMessageRequest;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatMessageResponse;
import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;
import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;
import goormthonuniv.team_7_be.api.chat.entity.MessageReceipt;
import goormthonuniv.team_7_be.api.chat.entity.MessageReceiptStatus;
import goormthonuniv.team_7_be.api.chat.exception.ChatExceptionType;
import goormthonuniv.team_7_be.api.chat.repository.ChatMessageRepository;
import goormthonuniv.team_7_be.api.chat.repository.ChatRoomRepository;
import goormthonuniv.team_7_be.api.chat.repository.MessageReceiptRepository;
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
    private final MessageReceiptRepository messageReceiptRepository;

    // 메시지 전송
    public ChatMessageResponse send(ChatMessageRequest request, String email) {
        Member sender = memberRepository.findByEmail(email)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(request.chatRoomId())
            .orElseThrow(() -> new BaseException(ChatExceptionType.CHAT_ROOM_NOT_FOUND));

        ChatMessage chatMessage = ChatMessage.builder()
            .chatRoom(chatRoom)
            .sender(sender)
            .content(request.content())
            .messageType(request.messageType())
            .messageContentType(request.contentType())
            .build();

        chatMessageRepository.save(chatMessage);

        // 수신자 찾기
        Member receiver = Objects.equals(chatRoom.getMember1().getId(), sender.getId()) ? chatRoom.getMember2() :
            chatRoom.getMember1();

        // 메시지 수신 정보 저장
        MessageReceipt messageReceipt = MessageReceipt.builder()
            .chatMessage(chatMessage)
            .member(receiver)
            .chatRoom(chatRoom)
            .status(MessageReceiptStatus.DELIVERED)
            .build();
        messageReceiptRepository.save(messageReceipt);

        return ChatMessageResponse.from(chatMessage);
    }

    @Transactional
    public void readMessages(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new BaseException(ChatExceptionType.CHAT_ROOM_NOT_FOUND));

        if (!Objects.equals(chatRoom.getMember1().getId(), memberId) && !Objects.equals(chatRoom.getMember2().getId(),
            memberId)) {
            throw new BaseException(ChatExceptionType.CHAT_ROOM_ACCESS_DENIED);
        }
        messageReceiptRepository.bulkUpdateStatusToRead(chatRoomId, memberId);
    }
}

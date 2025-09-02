package goormthonuniv.team_7_be.api.chat.service;

import goormthonuniv.team_7_be.api.chat.dto.request.ChatRoomCreateRequest;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatMessageResponse;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatRoomListResponse;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatRoomResponse;
import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;
import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MessageReceiptRepository messageReceiptRepository;

    public ChatRoomResponse createChatRoom(String username, ChatRoomCreateRequest request) {
        // Member 조회
        Member me = memberRepository.findByEmail(username)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));
        Member opponent = memberRepository.findById(request.opponentId())
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        // 기존 방이 있는지 확인
        ChatRoom existingRoom = chatRoomRepository.findByMember1IdAndMember2Id(me.getId(), opponent.getId())
            .or(() -> chatRoomRepository.findByMember2IdAndMember1Id(me.getId(), opponent.getId()))
            .orElse(null);

        if (existingRoom != null)
            return ChatRoomResponse.from(existingRoom, me, opponent);

        // 새 방 생성
        ChatRoom newRoom = ChatRoom.builder()
            .member1(me)
            .member2(opponent)
            .build();

        ChatRoom chatRoom = chatRoomRepository.save(newRoom);

        return ChatRoomResponse.from(chatRoom, me, opponent);
    }

    // 내가 참여한 채팅방 목록
    @Transactional(readOnly = true)
    public List<ChatRoomListResponse> getMyChatRooms(String currentMemberEmail) {
        Member currentMember = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        return chatRoomRepository.findByMember1IdOrMember2Id(currentMember.getId(), currentMember.getId())
                .stream()
                .map(room -> {
                    Member opponent = Objects.equals(room.getMember1().getId(), currentMember.getId()) ? room.getMember2() : room.getMember1();
                    ChatMessage lastMessage = chatMessageRepository.findTopByChatRoomOrderByCreatedAtDesc(room);
                    long unreadCount = messageReceiptRepository.countByChatRoomIdAndMemberIdAndStatus(
                            room.getId(), currentMember.getId(), MessageReceiptStatus.DELIVERED);

                    return ChatRoomListResponse.from(room, opponent, lastMessage, (int) unreadCount);
                })
                .sorted(Comparator.comparing(ChatRoomListResponse::getLastMessageAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    // 특정 채팅방 메시지 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getChatMessages(Long chatRoomId, String username) {
        Member member = memberRepository.findByEmail(username)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

         // 채팅방 존재 여부 및 접근 권한 확인
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new BaseException(ChatExceptionType.CHAT_ROOM_NOT_FOUND));

        if (!chatRoom.getMember1().getId().equals(member.getId()) && !chatRoom.getMember2().getId().equals(member.getId())) {
            throw new BaseException(ChatExceptionType.CHAT_ROOM_ACCESS_DENIED);
        }

        return chatMessageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId)
            .stream()
            .map(ChatMessageResponse::from)
            .toList();
    }
}

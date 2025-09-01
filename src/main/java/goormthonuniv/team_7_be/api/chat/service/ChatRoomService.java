package goormthonuniv.team_7_be.api.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goormthonuniv.team_7_be.api.chat.dto.request.ChatRoomCreateRequest;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatMessageResponse;
import goormthonuniv.team_7_be.api.chat.dto.response.ChatRoomResponse;
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
public class ChatRoomService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomResponse createChatRoom(Long memberId, ChatRoomCreateRequest request) {
        // Member 조회
        Member me = memberRepository.findById(memberId)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));
        Member opponent = memberRepository.findById(request.opponentId())
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        // 기존 방이 있는지 확인
        ChatRoom existingRoom = chatRoomRepository.findByMember1IdAndMember2Id(memberId, opponent.getId())
            .or(() -> chatRoomRepository.findByMember2IdAndMember1Id(memberId, opponent.getId()))
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
    public List<ChatRoomResponse> getMyChatRooms(Long currentMemberId) {
        return chatRoomRepository.findByMember1IdOrMember2Id(currentMemberId, currentMemberId)
            .stream()
            .map(room -> {
                Member member1 = room.getMember1();
                Member member2 = room.getMember2();
                return ChatRoomResponse.from(room, member1, member2);
            })
            .toList();
    }

    // 특정 채팅방 메시지 조회
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getChatMessages(Long chatRoomId, Long memberId) {
         // 채팅방 존재 여부 및 접근 권한 확인
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new BaseException(ChatExceptionType.CHAT_ROOM_NOT_FOUND));

        if (!chatRoom.getMember1().getId().equals(memberId) && !chatRoom.getMember2().getId().equals(memberId)) {
            throw new BaseException(ChatExceptionType.CHAT_ROOM_ACCESS_DENIED);
        }

        return chatMessageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId)
            .stream()
            .map(ChatMessageResponse::from)
            .toList();
    }
}
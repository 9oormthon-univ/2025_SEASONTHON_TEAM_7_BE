package goormthonuniv.team_7_be.api.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 두 멤버가 동일한 채팅방을 이미 가지고 있는지 확인 (중복 방 생성 방지)
    Optional<ChatRoom> findByMember1IdAndMember2Id(Long member1Id, Long member2Id);

    Optional<ChatRoom> findByMember2IdAndMember1Id(Long member2Id, Long member1Id);

    // 특정 회원이 참여한 모든 채팅방
    List<ChatRoom> findByMember1IdOrMember2Id(Long member1Id, Long member2Id);

    @Query("SELECT CASE WHEN EXISTS (" +
        "SELECT 1 FROM ChatRoom c " +
        "WHERE (c.member1.id = :memberId AND c.member2.id = :opponentId) " +
        "   OR (c.member1.id = :opponentId AND c.member2.id = :memberId)) " +
        "THEN true ELSE false END")
    Boolean existsByIdAndOpponentId(Long memberId, Long opponentId);
}
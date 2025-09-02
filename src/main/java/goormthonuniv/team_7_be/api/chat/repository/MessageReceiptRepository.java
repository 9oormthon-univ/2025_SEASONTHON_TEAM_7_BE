package goormthonuniv.team_7_be.api.chat.repository;

import goormthonuniv.team_7_be.api.chat.entity.MessageReceipt;
import goormthonuniv.team_7_be.api.chat.entity.MessageReceiptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageReceiptRepository extends JpaRepository<MessageReceipt, Long> {
    long countByChatRoomIdAndMemberIdAndStatus(Long chatRoomId, Long memberId, MessageReceiptStatus status);

    @Modifying
    @Query("update MessageReceipt mr set mr.status = 'READ' where mr.chatRoom.id = :chatRoomId and mr.member.id = :memberId and mr.status = 'UNREAD'")
    void bulkUpdateStatusToRead(@Param("chatRoomId") Long chatRoomId, @Param("memberId") Long memberId);
}

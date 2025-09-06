package goormthonuniv.team_7_be.api.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.notification.entity.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiverOrderByCreatedAtDesc(Member receiver);

    List<Notification> findAllByOrderByCreatedAtDesc();

    @Modifying(clearAutomatically = true) // 벌크 연산 후 영속성 컨텍스트를 초기화하여 데이터 불일치를 방지합니다.
    @Query("UPDATE Notification n SET n.status = 'READ', n.readAt = CURRENT_TIMESTAMP " +
            "WHERE n.receiver = :receiver AND n.status = 'UNREAD'")
    void updateAllToReadByReceiver(@Param("receiver") Member receiver);

}

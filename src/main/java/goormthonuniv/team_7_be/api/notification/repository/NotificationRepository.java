package goormthonuniv.team_7_be.api.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiverOrderByCreatedAtDesc(Member receiver);

    List<Notification> findAllByOrderByCreatedAtDesc();
}

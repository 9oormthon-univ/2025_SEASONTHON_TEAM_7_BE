package goormthonuniv.team_7_be.api.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import goormthonuniv.team_7_be.api.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

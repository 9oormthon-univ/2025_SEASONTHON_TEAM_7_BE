package goormthonuniv.team_7_be.api.notification.dto.response;

import java.time.LocalDateTime;

import goormthonuniv.team_7_be.api.notification.entity.Notification;
import goormthonuniv.team_7_be.api.notification.entity.NotificationStatus;

public record NotificationResponse(
    Long id,
    Long receiverId,
    String receiverName,
    String type,
    Long referencedId,
    String message,
    NotificationStatus status,
    LocalDateTime createdAt,
    LocalDateTime readAt
) {
    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
            notification.getId(),
            notification.getReceiver().getId(),
            notification.getReceiver().getNickname(),
            notification.getType().getTitle(),
            notification.getReferencedId(),
            notification.getMessage(),
            notification.getStatus(),
            notification.getCreatedAt(),
            notification.getReadAt()
        );
    }
}

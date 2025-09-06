package goormthonuniv.team_7_be.api.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.notification.entity.Notification;
import goormthonuniv.team_7_be.api.notification.entity.NotificationType;
import goormthonuniv.team_7_be.api.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void notifyCoffeeChatRequested(Member sender, Member receiver) {
        String senderName = displayName(sender);
        String message = String.format("%s 님이 커피챗을 요청했습니다.", senderName);
        save(receiver, NotificationType.COFFEE_CHAT_REQUEST, message);
    }

    public void notifyCoffeeChatDeclined(Member decliner, Member requester) {
        String declinerName = displayName(decliner);
        String message = String.format("%s 님이 커피챗 요청을 거절했습니다.", declinerName);
        save(requester, NotificationType.COFFEE_CHAT_DECLINE, message);
    }

    public void notifyMannerReviewCreated(Member reviewer, Member target, int rate, String review) {
        String reviewerName = displayName(reviewer);
        String message = String.format("%s 님이 매너 후기를 남겼습니다. (평점: %d, 내용: %s)", reviewerName, rate, review == null ? "" : review);
        save(target, NotificationType.MANNER_REVIEW, message);
    }

    private String displayName(Member member) {
        return member.getNickname() != null ? member.getNickname() : member.getEmail();
    }

    private void save(Member receiver, NotificationType type, String message) {
        notificationRepository.save(
            Notification.builder()
                .receiver(receiver)
                .type(type)
                .message(message)
                .build()
        );
    }
}

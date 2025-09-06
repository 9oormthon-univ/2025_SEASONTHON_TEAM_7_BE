package goormthonuniv.team_7_be.api.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.api.notification.dto.response.NotificationResponse;
import goormthonuniv.team_7_be.api.notification.entity.Notification;
import goormthonuniv.team_7_be.api.notification.entity.NotificationType;
import goormthonuniv.team_7_be.api.notification.repository.NotificationRepository;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAllByOrderByCreatedAtDesc();
        return notifications.stream()
            .map(NotificationResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getMyNotifications(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        List<Notification> notifications = notificationRepository.findAllByReceiverOrderByCreatedAtDesc(member);
        return notifications.stream()
            .map(NotificationResponse::from)
            .toList();
    }

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

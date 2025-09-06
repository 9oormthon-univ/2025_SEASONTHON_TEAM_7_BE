package goormthonuniv.team_7_be.api.notification.service;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.api.notification.dto.response.NotificationResponse;
import goormthonuniv.team_7_be.api.notification.entity.Notification;
import goormthonuniv.team_7_be.api.notification.entity.NotificationType;
import goormthonuniv.team_7_be.api.notification.repository.NotificationRepository;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public void notifyCoffeeChatRequested(Member sender, Member receiver, Long referencedId) {
        String senderName = displayName(sender);
        String message = String.format("%s 님이 커피챗을 요청했습니다.", senderName);
        notificationRepository.save(
                Notification.builder()
                        .receiver(receiver)
                        .type(NotificationType.COFFEE_CHAT_REQUEST)
                        .referencedId(referencedId)
                        .message(message)
                        .build()
        );
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

    @Transactional
    public void markAsRead(Long notificationId, String email) {
        // 1. notificationId로 알림을 조회합니다.
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        // 2. 해당 알림의 수신자(receiver)가 현재 사용자가 맞는지 확인합니다. (중요한 인가 절차)
        if (!notification.getReceiver().getEmail().equals(email)) {
            throw new BaseException(MemberExceptionType.MEMBER_EXCEPTION_TYPE); // 권한 없음 예외 처리
        }

        // 3. 알림의 상태를 '읽음'으로 변경합니다. (JPA의 Dirty Checking 활용)
        notification.markAsRead();
    }

    @Transactional
    public void markAllAsRead(String email) {
        // 1. 이메일을 통해 Member 엔티티를 조회합니다.
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        // 2. 해당 Member의 모든 알림을 읽음 처리하는 벌크 업데이트 쿼리를 실행합니다.
        notificationRepository.updateAllToReadByReceiver(member);
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

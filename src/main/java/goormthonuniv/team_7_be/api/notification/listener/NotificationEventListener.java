package goormthonuniv.team_7_be.api.notification.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import goormthonuniv.team_7_be.api.notification.event.CoffeeChatDeclinedEvent;
import goormthonuniv.team_7_be.api.notification.event.CoffeeChatRequestedEvent;
import goormthonuniv.team_7_be.api.notification.event.MannerReviewCreatedEvent;
import goormthonuniv.team_7_be.api.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCoffeeChatRequested(CoffeeChatRequestedEvent event) {
        notificationService.notifyCoffeeChatRequested(event.sender(), event.receiver(), event.referencedId());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCoffeeChatDeclined(CoffeeChatDeclinedEvent event) {
        notificationService.notifyCoffeeChatDeclined(event.getDecliner(), event.getRequester());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onMannerReviewCreated(MannerReviewCreatedEvent event) {
        notificationService.notifyMannerReviewCreated(event.getReviewer(), event.getTarget(), event.getRate(), event.getReview());
    }
}

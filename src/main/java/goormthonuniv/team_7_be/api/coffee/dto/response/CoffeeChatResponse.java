package goormthonuniv.team_7_be.api.coffee.dto.response;

import java.time.LocalDateTime;

import goormthonuniv.team_7_be.api.coffee.dto.request.CoffeeChatCreateRequest;
import goormthonuniv.team_7_be.api.coffee.entity.CoffeeChat;

public record CoffeeChatResponse(
    Long id,
    Long senderId,
    String senderUsername,
    Long receiverId,
    String receiverUsername,
    String status,
    LocalDateTime createdAt
) {
    public static CoffeeChatResponse from(CoffeeChat coffeeChat) {
        return new CoffeeChatResponse(
            coffeeChat.getId(),
            coffeeChat.getSender().getId(),
            coffeeChat.getSender().getNickname(),
            coffeeChat.getReceiver().getId(),
            coffeeChat.getReceiver().getNickname(),
            coffeeChat.getStatus().name(),
            coffeeChat.getCreatedAt()
        );
    }
}

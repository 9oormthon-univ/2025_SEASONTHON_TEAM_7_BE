package goormthonuniv.team_7_be.api.coffee.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CoffeeChatCreateRequest(
    @NotNull(message = "receiverId는 필수입니다.")
    @Positive
    Long receiverId
) {
}

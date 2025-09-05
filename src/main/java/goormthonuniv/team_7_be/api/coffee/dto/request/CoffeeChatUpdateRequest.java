package goormthonuniv.team_7_be.api.coffee.dto.request;

import goormthonuniv.team_7_be.api.coffee.entity.CoffeeChatStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CoffeeChatUpdateRequest(
    @NotNull(message = "커피챗 ID는 필수입니다.")
    @Positive
    Long coffeeChatId,

    @Schema(description = "커피챗 상태", example = "ACCEPTED")
    @NotNull(message = "커피챗 상태는 필수입니다.")
    CoffeeChatStatus status
) {
}

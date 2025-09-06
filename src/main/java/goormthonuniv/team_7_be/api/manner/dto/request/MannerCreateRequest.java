package goormthonuniv.team_7_be.api.manner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MannerCreateRequest(
        @Schema(description = "상대방 멤버 ID", example = "1")
        @NotNull(message = "memberId는 필수입니다.")
        Long memberId,

        @Schema(description = "매너 점수", example = "5")
        @NotNull(message = "rate는 필수입니다.")
        Integer rate,

        @Schema(description = "매너 후기", example = "친절하고 성실한 분이었어요!")
        @NotNull(message = "review는 필수입니다.")
        String review
) {
}

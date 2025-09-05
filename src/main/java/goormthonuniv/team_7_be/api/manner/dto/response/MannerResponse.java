package goormthonuniv.team_7_be.api.manner.dto.response;

import goormthonuniv.team_7_be.api.manner.entity.Manner;

public record MannerResponse(
    Long id,
    Integer rate,
    String review
) {
    public static  MannerResponse from(Manner manner) {
        return new MannerResponse(
            manner.getId(),
            manner.getRate(),
            manner.getReview()
        );
    }
}

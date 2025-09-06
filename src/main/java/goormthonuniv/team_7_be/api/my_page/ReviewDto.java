package goormthonuniv.team_7_be.api.my_page;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewDto {
    private String reviewerName;
    private String reviewerProfileImageUrl;
    private String reviewerComment;
    private LocalDateTime reviewDate;
}
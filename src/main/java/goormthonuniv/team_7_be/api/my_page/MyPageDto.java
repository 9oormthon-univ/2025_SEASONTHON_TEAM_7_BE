package goormthonuniv.team_7_be.api.my_page;


import goormthonuniv.team_7_be.api.member.entity.InterestedJob;
import goormthonuniv.team_7_be.api.member.entity.MemberAge;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class MyPageDto {

    private MemberAge memberAge;
    private String nickname;
    private String profileImageUrl;
    public Double mannerScore;

    @Builder.Default
    private List<InterestedJob> interests = new ArrayList<InterestedJob>();
    private String introduceMySelf;


    private String reviewerName;
    private String reviewerComment;
    private LocalDateTime reviewDate;
    private String reviewerProfileImageUrl;
}

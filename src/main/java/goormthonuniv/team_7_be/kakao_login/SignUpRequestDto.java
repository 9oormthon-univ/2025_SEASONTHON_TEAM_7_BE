package goormthonuniv.team_7_be.kakao_login;

import goormthonuniv.team_7_be.api.member.entity.InterestedJob;
import goormthonuniv.team_7_be.api.member.entity.MemberAge;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    private String nickname;
    private List<InterestedJob> interests;
    private MemberAge memberAge;
    private String introduceMySelf;
}

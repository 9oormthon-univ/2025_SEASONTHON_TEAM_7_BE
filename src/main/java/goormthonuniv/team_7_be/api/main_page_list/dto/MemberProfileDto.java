package goormthonuniv.team_7_be.api.main_page_list.dto;

import goormthonuniv.team_7_be.api.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

public record MemberProfileDto(
        Long memberId,
        String profileImageUrl,
        String nickname,
        String age,
        String introduction,
        Double mannerScore,
        List<String> interests
) {
    public static MemberProfileDto from(Member member) {
        return new MemberProfileDto(
                member.getId(),
                member.getProfileImageUrl(),
                member.getNickname(),
                member.getMemberAge().getDisplayName(),
                member.getIntroduceMySelf(),
                member.getMannerScore(),
                member.getInterests().stream()
                        .map(interest -> "#" + interest.name())
                        .collect(Collectors.toList())
        );
    }
}
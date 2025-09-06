package goormthonuniv.team_7_be.api.member.dto;

import goormthonuniv.team_7_be.api.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record MemberProfileDto(
        Long memberId,
        String profileImageUrl,
        String nickname,
        String age,
        String introduction,
        Double mannerScore,
        List<String> interests,
        Boolean isActive,
        LocalDateTime lastActiveAt
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
                        .collect(Collectors.toList()),
                member.getIsActive(),
                member.getLastActiveAt()
        );
    }
}
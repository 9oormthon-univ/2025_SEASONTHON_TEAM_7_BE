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
        List<String> interests
) {
    // Member 엔티티를 이 DTO 형태로 변환해주는 메서드
    public static MemberProfileDto from(Member member) {
        return new MemberProfileDto(
                member.getId(),
                member.getProfileImageUrl(),
                member.getNickname(),
                member.getMemberAge().getDisplayName(),
                member.getIntroduceMySelf(),
                member.getInterests().stream()
                        .map(interest -> "#" + interest.name())
                        .collect(Collectors.toList())
                // 찻잔지수 ?
        );
    }
}
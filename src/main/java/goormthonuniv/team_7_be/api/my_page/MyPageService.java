package goormthonuniv.team_7_be.api.my_page;


import goormthonuniv.team_7_be.api.manner.entity.Manner;
import goormthonuniv.team_7_be.api.manner.repository.MannerRepository;
import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MannerRepository mannerRepository; // ReviewRepository -> MannerRepository로 변경

    @Transactional(readOnly = true)
    public MyPageDto getMyPageInfo(Long memberId) {
        // 1. 현재 로그인한 사용자(마이페이지의 주인)의 Member 엔티티를 조회합니다.
        Member currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. ID: " + memberId));

        // 2. 현재 사용자가 '받은(receiver)' 가장 최근의 Manner 1개를 조회합니다.
        Optional<Manner> latestMannerOpt = mannerRepository.findTopByReceiverOrderByCreatedAtDesc(currentMember);

        // 3. DTO 빌더를 사용하여 사용자 정보를 먼저 채웁니다.
        MyPageDto.MyPageDtoBuilder dtoBuilder = MyPageDto.builder()
                .memberAge(currentMember.getMemberAge())
                .nickname(currentMember.getNickname())
                .profileImageUrl(currentMember.getProfileImageUrl())
                .mannerScore(currentMember.getMannerScore())
                .interests(currentMember.getInterests())
                .introduceMySelf(currentMember.getIntroduceMySelf());

        // 4. 조회된 최신 매너 평가가 있다면, DTO에 평가 관련 정보를 추가합니다.
        if (latestMannerOpt.isPresent()) {
            Manner latestManner = latestMannerOpt.get();
            dtoBuilder.reviewerName(latestManner.getReviewer().getNickname()); // 평가를 '한' 사람의 닉네임
            dtoBuilder.reviewerComment(latestManner.getReview());
            dtoBuilder.reviewDate(latestManner.getCreatedAt());
        }

        // 5. 최종 DTO를 빌드하여 반환합니다.
        return dtoBuilder.build();
    }
}

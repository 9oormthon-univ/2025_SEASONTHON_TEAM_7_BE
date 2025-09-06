package goormthonuniv.team_7_be.api.my_page;


import goormthonuniv.team_7_be.api.manner.entity.Manner;
import goormthonuniv.team_7_be.api.manner.repository.MannerRepository;
import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final MannerRepository mannerRepository; // Manner(매너 평가) 정보를 다루는 리포지토리

    @Transactional(readOnly = true)
    public MyPageDto getMyPageInfo(String username) {
        // 1. 마이페이지 주인(현재 사용자) 정보 조회
        Member currentMember = memberRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(MemberExceptionType.MEMBER_NOT_FOUND));

        // 2. 사용자가 받은 가장 최근 매너 평가 조회 (Manner 엔티티 기준)
        //    MannerRepository에 이 메소드가 정의되어 있어야 합니다.
        Optional<Manner> latestMannerOpt = mannerRepository.findTopByReceiverOrderByCreatedAtDesc(currentMember);

        // 3. DTO 빌더를 사용하여 기본 사용자 정보 채우기
        MyPageDto.MyPageDtoBuilder dtoBuilder = MyPageDto.builder()
                .memberAge(currentMember.getMemberAge())
                .nickname(currentMember.getNickname())
                .profileImageUrl(currentMember.getProfileImageUrl())
                .mannerScore(currentMember.getMannerScore())
                .interests(currentMember.getInterests())
                .introduceMySelf(currentMember.getIntroduceMySelf());

        // 4. 조회된 최신 매너 평가가 있다면, 평가자(reviewer) 관련 정보 추가
        if (latestMannerOpt.isPresent()) {
            Manner latestManner = latestMannerOpt.get();
            Member reviewer = latestManner.getReviewer(); // 평가를 한 사람

            dtoBuilder.reviewerName(reviewer.getNickname());
            dtoBuilder.reviewerProfileImageUrl(reviewer.getProfileImageUrl()); // DTO에 추가된 필드
            dtoBuilder.reviewerComment(latestManner.getReview());
            dtoBuilder.reviewDate(latestManner.getCreatedAt());
        }

        // 5. 최종 DTO 객체 생성 및 반환
        return dtoBuilder.build();
    }
}

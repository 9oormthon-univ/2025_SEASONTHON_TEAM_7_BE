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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // 2. [수정] 사용자가 받은 '모든' 매너 평가를 최신순으로 조회 (Fetch Join 적용)
        List<Manner> receivedManners = mannerRepository.findAllByReceiverWithReviewer(currentMember);

        // 3. [신규] 조회된 Manner 엔티티 리스트를 ReviewDto 리스트로 변환
        List<ReviewDto> reviewDtos = receivedManners.stream()
                .map(manner -> {
                    Member reviewer = manner.getReviewer();
                    return ReviewDto.builder()
                            .reviewerName(reviewer.getNickname())
                            .reviewerProfileImageUrl(reviewer.getProfileImageUrl())
                            .reviewerComment(manner.getReview())
                            .reviewDate(manner.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

        // 4. 최종 DTO 빌드 (기본 사용자 정보 + 변환된 리뷰 DTO 리스트)
        return MyPageDto.builder()
                .memberAge(currentMember.getMemberAge())
                .nickname(currentMember.getNickname())
                .profileImageUrl(currentMember.getProfileImageUrl())
                .mannerScore(currentMember.getMannerScore())
                .interests(currentMember.getInterests())
                .introduceMySelf(currentMember.getIntroduceMySelf())
                .reviews(reviewDtos) // [수정] DTO 리스트를 설정
                .build();
    }
}

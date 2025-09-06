package goormthonuniv.team_7_be.kakao_login;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.utils.JwtProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenDto completeSignUp(String email, String profileImageUrl, SignUpRequestDto requestDto) {

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        member.updateProfileImageUrl(profileImageUrl);

        // 추가 정보 입력 (닉네임, 관심 직군)
        member.completeSignUp(
                requestDto.getNickname(),
                requestDto.getInterests(),
                requestDto.getIntroduceMySelf(),
                requestDto.getMemberAge()
        );

        // ★ JWT 토큰 생성 로직 추가
        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = jwtProvider.generateRefreshToken(email);

        // ★ 생성된 Refresh Token을 Member 엔티티에 저장
        member.updateRefreshToken(refreshToken);

        // ★ 생성된 토큰들을 DTO에 담아 반환
        return TokenDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Transactional(readOnly = true)
    public Boolean validateNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
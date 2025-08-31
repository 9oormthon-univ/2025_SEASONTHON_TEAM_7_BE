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
    private final JwtProvider jwtProvider; // ★ 주석 해제 및 final 추가

    @Transactional
    // ★ 반환 타입을 TokenDto로 변경
    public TokenDto completeSignUp(String email, SignUpRequestDto requestDto) {

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 추가 정보 입력 (닉네임, 관심 직군)
        member.completeSignUp(requestDto.getNickname(), requestDto.getInterestedJob());

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
}
package goormthonuniv.team_7_be.common.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import goormthonuniv.team_7_be.api.member.entity.MemberRole;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.utils.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        boolean isGuest = oAuth2User.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(MemberRole.GUEST.getKey()));

        // ★ 수정된 부분: 이메일 정보는 공통으로 사용하므로 미리 추출
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        String email = (String)kakaoAccount.get("email");


        log.info(attributes.toString());

        String targetUrl;

        if (isGuest) {
            // ★ 수정된 부분: GUEST일 경우 임시 토큰 발급 및 URL에 추가
            log.info("신규 사용자입니다. 회원가입용 임시 토큰을 발급하여 리디렉션합니다. email={}", email);

            String signupToken = jwtProvider.generateSignupToken(email);

            targetUrl = UriComponentsBuilder.fromUriString("https://teetalk.vercel.app//signup/extra-info")
                    .queryParam("signupToken", signupToken)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();

        } else {
            // 권한이 USER인 경우 (기존 사용자)
            log.info("기존 사용자입니다. JWT를 발급하고 메인 페이지로 리디렉션합니다. email={}", email);

            String accessToken = jwtProvider.generateAccessToken(email);
            String refreshToken = jwtProvider.generateRefreshToken(email);

            log.info("Access Token: {}", accessToken);

            memberRepository.findByEmail(email).ifPresent(member -> {
                member.updateRefreshToken(refreshToken);
                memberRepository.save(member);
            });

            targetUrl = UriComponentsBuilder.fromUriString("https://teetalk.vercel.app/")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
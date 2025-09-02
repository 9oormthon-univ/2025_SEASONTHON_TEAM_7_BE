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

        // 사용자의 권한을 확인합니다.
        boolean isGuest = oAuth2User.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(MemberRole.GUEST.getKey()));

        String targetUrl;

        if (isGuest) {
            // 권한이 GUEST인 경우 (신규 사용자)
            log.info("신규 사용자입니다. 추가 정보 입력 페이지로 리디렉션합니다.");
            // ★ 프론트엔드의 추가 정보 입력 페이지 URL로 변경해야 합니다.
            targetUrl = "http://localhost:3000/signup/extra-info";
        } else {
            // 권한이 USER인 경우 (기존 사용자)
            log.info("기존 사용자입니다. JWT를 발급하고 메인 페이지로 리디렉션합니다.");

            // JWT 토큰을 생성합니다.
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
            String email = (String)kakaoAccount.get("email");

            String accessToken = jwtProvider.generateAccessToken(email);
            String refreshToken = jwtProvider.generateRefreshToken(email);

            log.info("Access Token: {}", accessToken);

            // DB에 리프레시 토큰을 저장합니다.
            memberRepository.findByEmail(email).ifPresent(member -> {
                member.updateRefreshToken(refreshToken);
                memberRepository.save(member);
            });

            // ★ 프론트엔드의 메인 페이지 URL로 변경해야 합니다.
            targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        }

        // 사용자를 targetUrl로 리디렉션시킵니다.
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
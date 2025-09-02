package goormthonuniv.team_7_be.common.auth.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.utils.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        String token = jwtProvider.resolveToken(request);

        if (token != null && jwtProvider.validateToken(token)) {
            String email = jwtProvider.getEmailFromToken(token);

            // findByUsername -> findByEmail로 변경
            memberRepository.findByEmail(email).ifPresent(member -> {
                // 사용자 속성 맵 생성
                Map<String, Object> attributes = Map.of(
                    "id", member.getId(),
                    "email", member.getEmail(),
                    "role", member.getRole().getKey()
                );

                // OAuth2User 객체 생성
                DefaultOAuth2User oauth2User = new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                    attributes,
                    "id"
                );

                // OAuth2AuthenticationToken 생성
                OAuth2AuthenticationToken authToken = new OAuth2AuthenticationToken(
                    oauth2User,
                    Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                    "kakao"
                );

                // SecurityContextHolder에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authToken);
            });
        }
        filterChain.doFilter(request, response);
    }
}

package goormthonuniv.team_7_be.common.auth.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    // ★ 수정된 부분: MemberRepository 다시 주입받기
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.startsWith("/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtProvider.resolveToken(request);

        if (token != null && jwtProvider.validateToken(token)) {
            String email = jwtProvider.getEmailFromToken(token);

            // ★ 수정된 부분: DB에서 사용자를 조회하여 인증 정보를 다르게 생성
            memberRepository.findByEmail(email).ifPresentOrElse(
                    // Case 1: DB에 사용자가 있는 경우 (이미 회원가입 및 로그인된 사용자)
                    member -> {
                        // DB에 저장된 실제 권한으로 인증 정보 생성
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                member.getEmail(), "",
                                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    },
                    // Case 2: DB에 사용자가 없는 경우 (회원가입 진행 중인 사용자)
                    () -> {
                        // 임시 인증 객체 생성
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                email, "",
                                Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST"))); // GUEST 권한 부여
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            );
        }
        filterChain.doFilter(request, response);
    }
}
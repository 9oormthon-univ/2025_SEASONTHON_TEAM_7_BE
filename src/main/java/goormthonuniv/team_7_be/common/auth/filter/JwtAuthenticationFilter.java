package goormthonuniv.team_7_be.common.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.auth.service.CustomUserDetails;
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
            memberRepository.findByEmail(email).ifPresent(member -> { // user -> member로 변경
                CustomUserDetails userDetails = new CustomUserDetails(member); // member 객체 사용
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }
        filterChain.doFilter(request, response);
    }
}

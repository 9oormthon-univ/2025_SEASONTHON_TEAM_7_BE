package goormthonuniv.team_7_be.common.handler;

import java.security.Principal;
import java.util.Collections;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            return message;
        }

        StompCommand command = accessor.getCommand();
        if (StompCommand.CONNECT.equals(command)) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 토큰 검증 후 인증 객체 생성
            if (token != null && jwtProvider.validateToken(token)) {
                String email = jwtProvider.getEmailFromToken(token);

                memberRepository.findByEmail(email).ifPresent(member -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                        email, null,
                        Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()))
                    );
                    // SecurityContext에도 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // STOMP 세션 사용자로도 설정 -> @MessageMapping Principal로 전달됨
                    accessor.setUser(authentication);
                    log.info("STOMP 연결 인증 성공 및 Principal 설정: {}", email);
                });
            } else {
                log.warn("STOMP CONNECT 토큰 검증 실패 또는 토큰 누락");
            }
        } else if (StompCommand.SEND.equals(command) || StompCommand.SUBSCRIBE.equals(command)) {
            // 이후 프레임에서 Principal이 비어있는 경우 SecurityContext에서 보완
            Principal user = accessor.getUser();
            if (user == null) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    accessor.setUser(auth);
                }
            }
        }

        return message;
    }
}

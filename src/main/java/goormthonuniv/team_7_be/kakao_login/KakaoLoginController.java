package goormthonuniv.team_7_be.kakao_login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/signup")
    // ★ 반환 타입을 ResponseEntity<TokenDto>로 변경
    public ResponseEntity<TokenDto> completeSignUp(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @RequestBody SignUpRequestDto requestDto) {
        String email = oAuth2User.getAttribute("email");

        // ★ 서비스에서 TokenDto를 반환받음
        TokenDto tokenDto = kakaoLoginService.completeSignUp(email, requestDto);

        // ★ 반환받은 토큰을 클라이언트에게 전달
        return ResponseEntity.ok(tokenDto);
    }
}
package goormthonuniv.team_7_be.kakao_login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> completeSignUp(
            // ★ 수정된 부분: @AuthenticationPrincipal 파라미터 제거
            @RequestBody SignUpRequestDto requestDto) {

        // ★ 수정된 부분: SecurityContext에서 직접 인증 정보를 가져와 이메일 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

//        String profileImageUrl = null;
//        if (authentication.getPrincipal() instanceof OAuth2User) {
//            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//            Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
//            if (kakaoAccount != null) {
//                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//                if (profile != null) {
//                    profileImageUrl = (String) profile.get("profile_image_url");
//                }
//            }
//        }

        TokenDto tokenDto = kakaoLoginService.completeSignUp(email,requestDto);

        return ResponseEntity.ok(tokenDto);
    }
}
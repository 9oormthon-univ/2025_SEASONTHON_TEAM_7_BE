package goormthonuniv.team_7_be.kakao_login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import goormthonuniv.team_7_be.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Tag(name = "Auth", description = "회원가입 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoLoginController {

    private static final Logger log = LoggerFactory.getLogger(KakaoLoginController.class);
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> completeSignUp(
            // ★ 수정된 부분: @AuthenticationPrincipal 파라미터 제거
            @RequestBody SignUpRequestDto requestDto) {

        // ★ 수정된 부분: SecurityContext에서 직접 인증 정보를 가져와 이메일 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        String profileImageUrl = null;
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

            // 1. 전체 속성 맵을 가져옵니다.
            Map<String, Object> attributes = oauth2User.getAttributes();


            // 2. 'properties' 라는 키로 사용자 정보 맵을 가져옵니다. (기존의 'kakao_account' 대신)
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

            if (properties != null) {
                // 3. 'properties' 맵에서 'profile_image_url'을 가져옵니다.
                profileImageUrl = (String) properties.get("profile_image");
            }
        }
        System.out.println("가져온 프로필 이미지 URL: " + profileImageUrl);

        TokenDto tokenDto = kakaoLoginService.completeSignUp(email,profileImageUrl,requestDto);

        return ResponseEntity.ok(tokenDto);
    }

    @Operation(summary = "닉네임 검증 API", description = "닉네임 중복을 검증합니다")
    @PostMapping
    public ApiResponse<Boolean> validateNickname(@RequestParam String nickname) {
        return ApiResponse.success(kakaoLoginService.validateNickname(nickname));
    }
}
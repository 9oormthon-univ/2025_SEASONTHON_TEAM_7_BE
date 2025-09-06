package goormthonuniv.team_7_be.kakao_login;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
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
import java.util.Optional;

@Tag(name = "Auth", description = "회원가입 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class KakaoLoginController {

    private static final Logger log = LoggerFactory.getLogger(KakaoLoginController.class);
    private final KakaoLoginService kakaoLoginService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> completeSignUp(
            // ★ 수정된 부분: @AuthenticationPrincipal 파라미터 제거
            @RequestBody SignUpRequestDto requestDto) {

        // ★ 수정된 부분: SecurityContext에서 직접 인증 정보를 가져와 이메일 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        TokenDto tokenDto = kakaoLoginService.completeSignUp(email,requestDto);

        return ResponseEntity.ok(tokenDto);
    }

    @Operation(summary = "닉네임 검증 API", description = "닉네임 중복을 검증합니다")
    @PostMapping
    public ApiResponse<Boolean> validateNickname(@RequestParam String nickname) {
        return ApiResponse.success(kakaoLoginService.validateNickname(nickname));
    }
}
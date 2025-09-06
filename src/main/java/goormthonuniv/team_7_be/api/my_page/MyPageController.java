package goormthonuniv.team_7_be.api.my_page;


import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.api.member.exception.MemberExceptionType;
import goormthonuniv.team_7_be.api.member.repository.MemberRepository;
import goormthonuniv.team_7_be.common.auth.resolver.Auth;
import goormthonuniv.team_7_be.common.exception.BaseException;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    @GetMapping
    public ApiResponse<MyPageDto> getMyPage(
            @Parameter(hidden = true) @Auth Long memberId) { // @Auth로 Long 타입의 memberId를 바로 주입받음

        MyPageDto myPageDto = myPageService.getMyPageInfo(memberId);
        return ApiResponse.success(myPageDto);
    }
}

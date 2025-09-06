package goormthonuniv.team_7_be.api.member.controller;


import goormthonuniv.team_7_be.api.member.dto.MemberProfileDto;
import goormthonuniv.team_7_be.api.member.service.MemberService;
import goormthonuniv.team_7_be.common.auth.resolver.Auth;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Members", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "전체 회원 프로필 조회", description = "메인 페이지 회원 프로필을 최신순으로 조회합니다. 자신의 프로필은 제외됩니다.")
    @GetMapping
    public ApiResponse<List<MemberProfileDto>> getMemberProfiles(@Parameter(hidden = true) @Auth String username) {
        return ApiResponse.success(memberService.findAllMemberProfiles(username));
    }

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/me")
    public ApiResponse<MemberProfileDto> getMemberProfileById(@Parameter(hidden = true) @Auth String username) {
        MemberProfileDto member = memberService.findMemberProfileById(username);
        return ApiResponse.success(member);
    }

    @Operation(summary = "커피챗 아이디로 프로필 조회")
    @GetMapping("/coffee")
    public ApiResponse<MemberProfileDto> getMemberProfile(
            @Parameter(hidden = true) @Auth String username,
            @RequestParam Long coffeeChatId
    ) {
        return ApiResponse.success(memberService.findMemberProfile(coffeeChatId));
    }
}

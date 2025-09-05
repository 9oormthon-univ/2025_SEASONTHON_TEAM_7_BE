package goormthonuniv.team_7_be.api.main_page_list.controller;


import goormthonuniv.team_7_be.api.main_page_list.dto.MemberProfileDto;
import goormthonuniv.team_7_be.api.main_page_list.service.MemberListService;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberListController {
    private final MemberListService memberListService;

    @GetMapping("/all-list")
    public ApiResponse<List<MemberProfileDto>> getMemberProfiles() {
        List<MemberProfileDto> members = memberListService.findAllMemberProfiles();
        return ApiResponse.success(members);
    }
}

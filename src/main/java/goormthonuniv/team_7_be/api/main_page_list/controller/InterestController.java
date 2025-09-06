package goormthonuniv.team_7_be.api.main_page_list.controller;

import goormthonuniv.team_7_be.api.member.entity.InterestedJob;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interests")
public class InterestController {
    // 메인페이지 최상단
    @GetMapping("/list")
    public ApiResponse<InterestedJob[]> getAllInterests() {
        return ApiResponse.success(InterestedJob.values());
    }

}
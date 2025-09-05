package goormthonuniv.team_7_be.api.manner.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goormthonuniv.team_7_be.api.manner.dto.request.MannerCreateRequest;
import goormthonuniv.team_7_be.api.manner.dto.response.MannerResponse;
import goormthonuniv.team_7_be.api.manner.service.MannerService;
import goormthonuniv.team_7_be.common.auth.resolver.Auth;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Manner", description = "매너 평가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/manners")
public class MannerController {

    private final MannerService mannerService;

    @PostMapping
    public ApiResponse<MannerResponse> createManner(
        @Parameter(hidden = true) @Auth String username,
        @Valid @RequestBody MannerCreateRequest request
    ) {
        MannerResponse response = mannerService.createManner(username, request);
        return ApiResponse.success(response);
    }
}

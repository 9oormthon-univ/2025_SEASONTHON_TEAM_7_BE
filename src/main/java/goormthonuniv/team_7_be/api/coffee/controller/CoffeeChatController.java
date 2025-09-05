package goormthonuniv.team_7_be.api.coffee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goormthonuniv.team_7_be.api.coffee.dto.request.CoffeeChatCreateRequest;
import goormthonuniv.team_7_be.api.coffee.dto.request.CoffeeChatUpdateRequest;
import goormthonuniv.team_7_be.api.coffee.service.CoffeeChatService;
import goormthonuniv.team_7_be.common.auth.resolver.Auth;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Coffee Chat", description = "커피챗 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/coffee-chats")
public class CoffeeChatController {

    private final CoffeeChatService coffeeChatService;

    @GetMapping
    @Operation(summary = "[관리자용] 커피챗 목록 조회", description = "모든 커피챗 목록을 조회합니다.")
    public ApiResponse<?> getAllCoffeeChats() {
        return ApiResponse.success(coffeeChatService.getAllCoffeeChats());
    }

    @GetMapping("/me")
    @Operation(summary = "내 커피챗 목록 조회", description = "자신이 참여한 커피챗 목록을 조회합니다. 자신이 보낸 커피챗과 받은 커피챗 모두 조회됩니다.")
    public ApiResponse<?> getMyCoffeeChats(@Parameter(hidden = true) @Auth String username) {
        return ApiResponse.success(coffeeChatService.getMyCoffeeChats(username));
    }

    @Operation(summary = "커피챗 요청 생성", description = "자신이 아닌 다른 사용자에게 커피챗 요청을 생성합니다.")
    @PostMapping
    public ApiResponse<Void> createCoffeeChat(
        @Parameter(hidden = true) @Auth String username,
        @Valid @RequestBody CoffeeChatCreateRequest request
    ) {
        coffeeChatService.createCoffeeChat(request, username);
        return ApiResponse.success();
    }

    @Operation(summary = "커피챗 요청 상태 변경", description = "커피챗 요청 상태를 변경합니다. <br>"
        + "[REQUESTED, ACCEPTED, DECLINED, COMPLETED] 상태로 변경할 수 있습니다.")
    @PatchMapping
    public ApiResponse<Void> acceptCoffeeChat(
        @Parameter(hidden = true) @Auth String username,
        @Valid @RequestBody CoffeeChatUpdateRequest request
    ) {
        coffeeChatService.updateStatus(request, username);
        return ApiResponse.success();
    }
}

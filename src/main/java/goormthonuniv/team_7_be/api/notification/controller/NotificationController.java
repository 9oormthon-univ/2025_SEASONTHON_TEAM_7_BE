package goormthonuniv.team_7_be.api.notification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goormthonuniv.team_7_be.api.notification.dto.response.NotificationResponse;
import goormthonuniv.team_7_be.api.notification.service.NotificationService;
import goormthonuniv.team_7_be.common.auth.resolver.Auth;
import goormthonuniv.team_7_be.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Notification", description = "알림 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "[관리자용] 모든 알림 조회", description = "모든 알림을 조회합니다.")
    @GetMapping
    public ApiResponse<List<NotificationResponse>> getAllNotifications() {
        return ApiResponse.success(notificationService.getAllNotifications());
    }

    @Operation(summary = "내 모든 알림 조회", description = "나의 모든 알림을 조회합니다")
    @GetMapping("/me")
    public ApiResponse<List<NotificationResponse>> getMyNotifications(@Parameter(hidden = true) @Auth String username) {
        return ApiResponse.success(notificationService.getMyNotifications(username));
    }

    @Operation(summary = "[미구현] 모든 알림 읽음 처리", description = "모든 알림을 읽음 처리합니다.")
    @PatchMapping
    public ApiResponse<Void> markAllAsRead() {
        return ApiResponse.success();
    }

    @Operation(summary = "[미구현] 특정 알림 읽음 처리", description = "특정 알림을 읽음 처리합니다.")
    @PatchMapping("/{notificationId}")
    public ApiResponse<Void> markAsRead(@PathVariable Long notificationId) {
        return ApiResponse.success();
    }
}

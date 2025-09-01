package goormthonuniv.team_7_be.api.chat.dto.response;

import java.time.LocalDateTime;

import goormthonuniv.team_7_be.api.chat.entity.MessageReceiptStatus;

public record MessageReceiptResponse(
    Long messageId,
    Long memberId,
    MessageReceiptStatus status,
    LocalDateTime updatedAt
) {}
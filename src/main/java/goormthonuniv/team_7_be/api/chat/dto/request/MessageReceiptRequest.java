package goormthonuniv.team_7_be.api.chat.dto.request;

import goormthonuniv.team_7_be.api.chat.entity.MessageReceiptStatus;

public record MessageReceiptRequest(
    Long messageId,
    Long memberId,
    MessageReceiptStatus status
) {}
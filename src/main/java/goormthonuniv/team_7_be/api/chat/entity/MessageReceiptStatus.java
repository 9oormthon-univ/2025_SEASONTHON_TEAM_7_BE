package goormthonuniv.team_7_be.api.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageReceiptStatus {

    SENT("sent", "전송됨"),
    DELIVERED("delivered", "읽지 않음"),
    READ("read", "읽음");

    private final String key;
    private final String title;
}

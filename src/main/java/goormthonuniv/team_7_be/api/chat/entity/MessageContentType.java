package goormthonuniv.team_7_be.api.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageContentType {

    TEXT("text", "텍스트"),
    IMAGE("image", "이미지"),
    FILE("file", "파일");

    private final String key;
    private final String title;
}

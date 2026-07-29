package com.seek.food.dto.Chat;

import java.time.LocalDateTime;

public class ChatRecordDTO {
    private Long recordId;
    private Long chatRoomId;
    private Long accountId;
    private Integer accountType;
    private String chatDescription;
    private String chatShowImageAddr;
    private LocalDateTime createTime;
    private LocalDateTime withdrawDeadline;
    private Boolean withdraw;
}

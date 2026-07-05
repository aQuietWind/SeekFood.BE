package com.seek.food.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OldFileDTO {
    private String fileAddr;
    private Long userId;
    private LocalDateTime createTime;
    private Boolean delete;
}

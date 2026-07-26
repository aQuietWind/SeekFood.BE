package com.seek.food.dto.Rider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDTO {
    private Long rider_id;
    private String rider_name;
    private String rider_code;
    private String rider_phone_number;
    private String rider_password;
    private String rider_person_image_addr;
    private Integer rider_sex;
    private LocalDateTime create_time;
    private Boolean is_delete;
}

package com.seek.food.dto.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long employeeId;
    private Long merchant_id;
    private String employee_name;
    private String employee_code;
    private String employee_phone_number;
    private String employee_addr;
    private String employee_person_image_addr;
    private Integer employee_month_salary;
    private Integer employee_year_salary;
    private String employee_description;
    private String employee_position_name;
    private String employee_dep_name;
    private LocalDateTime create_time;
    private Boolean is_resign;
    private Boolean is_delete;
}

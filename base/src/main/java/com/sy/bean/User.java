package com.sy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private String phone;
    private String sex;
    private Integer age;
    private String address;
    private String email;
}

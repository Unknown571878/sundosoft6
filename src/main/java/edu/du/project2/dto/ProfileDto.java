package edu.du.project2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private String loginId;
    private String email;
    private String name;
    private String tel;
    private String zipCode;
    private String address;
    private String detailAddress;
}

package edu.du.project2.dto;

import lombok.Data;

@Data
public class MemberRequest {
    String email;
    String password;
    String name;
    String tel;
    String address;
    String detailAddress;
}

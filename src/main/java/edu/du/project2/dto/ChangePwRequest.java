package edu.du.project2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePwRequest {
    private String loginId;
    private String oldPw;
    private String newPw;
    private String confirmPw;
}

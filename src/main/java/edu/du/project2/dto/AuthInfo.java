package edu.du.project2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class AuthInfo {

	private Long id;
	private String email;
	private String name;
	private String role;
}

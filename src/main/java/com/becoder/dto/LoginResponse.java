package com.becoder.dto;

import java.util.List;

import com.becoder.dto.UserReqDto.RoleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
public class LoginResponse {

	private UserResDto user;

	private String token;

}
package com.blog.api.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min = 4, message = "Name must be min of 4 charactars")
	private String name;
	@Email(message = "Email Address is not valid")
	private String email;
	@NotEmpty
	@Size(min = 3, max = 15, message = "Password must be min of 3 chars and max of 15 chars !!")
	private String password;
	@NotEmpty
	private String about;
	private Set<RoleDto> roles =new HashSet<>();
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password= password;
	}
	

	
}

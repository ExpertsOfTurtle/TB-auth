package com.turtlebone.auth.bean;

import lombok.Data;

@Data
public class ProfileRequest {
	protected String username;
	protected String password;
	protected String email;
}

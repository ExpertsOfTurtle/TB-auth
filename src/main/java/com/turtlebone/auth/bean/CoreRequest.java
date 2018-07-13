package com.turtlebone.auth.bean;

import lombok.Data;

@Data
public class CoreRequest {
	private String token;
	private String loginType;//1-username/psw, 2-Wechat
	private String username;
}

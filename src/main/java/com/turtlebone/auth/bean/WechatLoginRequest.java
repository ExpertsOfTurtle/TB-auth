package com.turtlebone.auth.bean;

import lombok.Data;

@Data
public class WechatLoginRequest extends CoreRequest {
	private String nickName;
	private String avatarUrl;
	private String code;
}

package com.turtlebone.auth.bean;

import lombok.Data;

@Data
public class CreateTokenRequest {
	protected String username;
	protected String datetime;
	protected String signData;
}

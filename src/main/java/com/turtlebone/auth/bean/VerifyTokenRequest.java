package com.turtlebone.auth.bean;

import lombok.Data;

@Data
public class VerifyTokenRequest {
	protected String tokenId;
	protected String username;
}

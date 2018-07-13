package com.turtlebone.auth.bean;

import lombok.Data;

@Data
public class RenewTokenRequest {
	protected String tokenId;
	protected String username;
	protected Long extendSecond;
}

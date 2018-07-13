package com.turtlebone.auth.model;
import java.io.Serializable;
import java.util.Date;

public class TokenModel implements Serializable{
	
	private Integer id;
	private String tokenid;
	private String username;
	private Date expirytime;
	private Date createtime;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setTokenid(String tokenid){
		this.tokenid = tokenid;
	}
	
	public String getTokenid(){
		return this.tokenid;
	}
		
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
		
	public void setExpirytime(Date expirytime){
		this.expirytime = expirytime;
	}
	
	public Date getExpirytime(){
		return this.expirytime;
	}
		
	public void setCreatetime(Date createtime){
		this.createtime = createtime;
	}
	
	public Date getCreatetime(){
		return this.createtime;
	}
		
		
}
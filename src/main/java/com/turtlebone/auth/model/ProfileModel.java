package com.turtlebone.auth.model;
import java.io.Serializable;
import java.util.Date;

public class ProfileModel implements Serializable{
	
	private Integer id;
	private String username;
	private Integer status;
	private Integer active;
	private Date lastlogontime;
	private String lastlogonip;
	private String password;
	private String email;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
		
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}
		
	public void setActive(Integer active){
		this.active = active;
	}
	
	public Integer getActive(){
		return this.active;
	}
		
	public void setLastlogontime(Date lastlogontime){
		this.lastlogontime = lastlogontime;
	}
	
	public Date getLastlogontime(){
		return this.lastlogontime;
	}
		
	public void setLastlogonip(String lastlogonip){
		this.lastlogonip = lastlogonip;
	}
	
	public String getLastlogonip(){
		return this.lastlogonip;
	}
		
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
		
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
		
}
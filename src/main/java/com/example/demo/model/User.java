package com.example.demo.model;

public class User {
	private String userId;
	private String userName;
	private int age;
	private String email;
	private String password;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String userId, String userName, int age, String email, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.age = age;
		this.email = email;
		this.password = password;
	}
	
	
	public User(String userId, String userName, int age, String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.age = age;
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	

}

package com.subhechhu.bhadama.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	@SerializedName("pin")
	private String pin;

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "User{" +
				"phoneNumber='" + phoneNumber + '\'' +
				", email='" + email + '\'' +
				", username='" + username + '\'' +
				", pin='" + pin + '\'' +
				'}';
	}
}
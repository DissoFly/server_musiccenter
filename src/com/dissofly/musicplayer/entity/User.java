package com.dissofly.musicplayer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.dissofly.musicplayer.util.DateRecord;

@Entity
public class User extends DateRecord implements Serializable{
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId", nullable = false)
	int userId;
	String account;
	String passwordHsah;
	String email;
	Long phoneNumber;
	Boolean isAdmin;
	Boolean isFreeze;
	String avatar;
	
	public Boolean getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(Boolean isFreeze) {
		this.isFreeze = isFreeze;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public User(){
		
	}
	public User(
	String account,
	String passwordHsah,
	String email,
	Long phoneNumber,
	Boolean isAdmin
	){
		this.account=account;
		this.passwordHsah=passwordHsah;
		this.email=email;
		this.phoneNumber=phoneNumber;
		this.isAdmin=isAdmin;
		this.isFreeze=false;
		onPrePersist();
		
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPasswordHsah() {
		return passwordHsah;
	}
	public void setPasswordHsah(String passwordHsah) {
		this.passwordHsah = passwordHsah;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	} 
	

}

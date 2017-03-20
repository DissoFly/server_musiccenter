package com.dissofly.musicplayer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dissofly.musicplayer.util.DateRecord;
@Entity
public class ClickLike extends DateRecord implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "likeId", nullable = false)
	int likeId;
	@Column(nullable = false)
	int userId;
	String userName;
	boolean isComment;
	boolean isSong;
	int beLikeId;
	String beLikeText;
	
	public String getBeLikeText() {
		return beLikeText;
	}
	public void setBeLikeText(String beLikeText) {
		this.beLikeText = beLikeText;
	}
	public int getLikeId() {
		return likeId;
	}
	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isComment() {
		return isComment;
	}
	public void setComment(boolean isComment) {
		this.isComment = isComment;
	}
	public boolean isSong() {
		return isSong;
	}
	public void setSong(boolean isSong) {
		this.isSong = isSong;
	}
	public int getBeLikeId() {
		return beLikeId;
	}
	public void setBeLikeId(int beLikeId) {
		this.beLikeId = beLikeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}

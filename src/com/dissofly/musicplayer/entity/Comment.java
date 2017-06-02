package com.dissofly.musicplayer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dissofly.musicplayer.util.DateRecord;
@Entity
public class Comment extends DateRecord implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "commentId", nullable = false)
	private int commentId;
	@Column(nullable = false)
	private int songId;
	private String text;
	@Column(nullable = false)
	private int floor;
	@Column(nullable = false)
	private int userId;
	private String userName;
	private String likeIds;
	private boolean isUnlook;
	private String unlookReason;
	private boolean isUserLike;
	
	public String getUnlookReason() {
		return unlookReason;
	}
	public void setUnlookReason(String unlookReason) {
		this.unlookReason = unlookReason;
	}
	public boolean isUserLike() {
		return isUserLike;
	}
	public void setUserLike(boolean isUserLike) {
		this.isUserLike = isUserLike;
	}
	public boolean isUnlook() {
		return isUnlook;
	}
	public void setUnlook(boolean isUnlook) {
		this.isUnlook = isUnlook;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLikeIds() {
		return likeIds;
	}
	public void setLikeIds(String likeIds) {
		this.likeIds = likeIds;
	}
	
	
}

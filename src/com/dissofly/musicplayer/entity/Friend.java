package com.dissofly.musicplayer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dissofly.musicplayer.util.DateRecord;

@Entity
public class Friend extends DateRecord implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "friendId", nullable = false)
	private int friendId;
	private int userId;
	private int friendUserId;
	private String friendName;
	private boolean isFollow;
	private boolean isBlacklist;
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(int friendUserId) {
		this.friendUserId = friendUserId;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public boolean isFollow() {
		return isFollow;
	}
	public void setFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}
	public boolean isBlacklist() {
		return isBlacklist;
	}
	public void setBlacklist(boolean isBlacklist) {
		this.isBlacklist = isBlacklist;
	}
	
}

package com.dissofly.musicplayer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dissofly.musicplayer.util.DateRecord;
@Entity
public class InboxList extends DateRecord implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "inboxListId", nullable = false)
	private int inboxListId;
	private String text;
	private int userId;
	private int friendId;
	private String friendName;
	private int unReadNumber;
	
	public void setNewOne(
			int userId,
			int friendId,
			String text,
			int unReadNumber){
		this.unReadNumber=unReadNumber;
		this.text=text;
		this.userId=userId;
		this.friendId=friendId;
		onPrePersist();
	}
	
	public void update(String text){
		this.text=text;
		addUnReadNumber();
		onPreUpdate();
	}
	
	public int addUnReadNumber(){
		unReadNumber++;
		return unReadNumber;
	}
	
	public int getInboxListId() {
		return inboxListId;
	}
	public void setInboxListId(int inboxListId) {
		this.inboxListId = inboxListId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public int getUnReadNumber() {
		return unReadNumber;
	}
	public void setUnReadNumber(int unReadNumber) {
		this.unReadNumber = unReadNumber;
	}
	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
}

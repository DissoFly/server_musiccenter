package com.dissofly.musicplayer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dissofly.musicplayer.util.DateRecord;

public class Inbox extends DateRecord implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "inboxId", nullable = false)
	private int inboxId;
	private int userId;
	private int geterId;
	private String text;
	private boolean isRead;
	public int getInboxId() {
		return inboxId;
	}
	public void setInboxId(int inboxId) {
		this.inboxId = inboxId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGeterId() {
		return geterId;
	}
	public void setGeterId(int geterId) {
		this.geterId = geterId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	

	
}

package com.dissofly.musicplayer.entity;

import java.io.Serializable;
import java.util.Date;

import com.dissofly.musicplayer.util.DateRecord;

public class FriendRead extends DateRecord implements Serializable, Comparable {
	private int readId;
	private int userId;
	private int songId;
	private String userName;
	private String text;
	private boolean isComment;
	private boolean isCommentLike;
	private boolean isMusicLike;

	public void setComment(int userId, int songId, String text,
			Date createDate, Date editDate) {
		this.userId = userId;
		this.songId = songId;
		this.text = text;
		setCreateDate(createDate);
		setEditDate(editDate);
		isComment = true;
		isCommentLike = false;
		isMusicLike = false;
	}

	public void setComment(String userName, int userId, int songId,
			String text, Date createDate, Date editDate) {
		this.userName = userName;
		setComment(userId, songId, text, createDate, editDate);
	}

	public void setMusicLike(int userId, int songId, Date createDate,
			Date editDate) {
		this.userId = userId;
		this.songId = songId;
		setCreateDate(createDate);
		setEditDate(editDate);
		isComment = false;
		isCommentLike = false;
		isMusicLike = true;
	}

	public void setMusicLike(String userName, int userId, int songId,
			Date createDate, Date editDate) {
		this.userName = userName;
		setMusicLike(userId, songId, createDate, editDate);
	}

	public void setCommentLike(int userId, int songId, String text,
			Date createDate, Date editDate) {
		this.userId = userId;
		this.songId = songId;
		this.text = text;
		setCreateDate(createDate);
		setEditDate(editDate);
		isComment = false;
		isCommentLike = true;
		isMusicLike = false;
	}

	public void setCommentLike(String userName, int userId, int songId,
			String text, Date createDate, Date editDate) {
		this.userName = userName;
		setCommentLike(userId, songId, text, createDate, editDate);
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public int getReadId() {
		return readId;
	}

	public void setReadId(int readId) {
		this.readId = readId;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isComment() {
		return isComment;
	}

	public void setComment(boolean isComment) {
		this.isComment = isComment;
	}

	public boolean isCommentLike() {
		return isCommentLike;
	}

	public void setCommentLike(boolean isCommentLike) {
		this.isCommentLike = isCommentLike;
	}

	public boolean isMusicLike() {
		return isMusicLike;
	}

	public void setMusicLike(boolean isMusicLike) {
		this.isMusicLike = isMusicLike;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return -this.getEditDate().compareTo(((FriendRead) arg0).getEditDate());
	}

}

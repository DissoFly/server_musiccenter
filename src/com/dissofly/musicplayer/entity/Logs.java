package com.dissofly.musicplayer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dissofly.musicplayer.util.DateRecord;

@Entity
public class Logs extends DateRecord implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "logsId", nullable = false)
	private int logsId;
	private int userId;
	private String entity;
	private String text;

	public void logsSave(int userId, String entity, String text) {
		this.userId=userId;
		this.entity=entity;
		this.text=text;
		onPrePersist();

	}

	public int getLogsId() {
		return logsId;
	}

	public void setLogsId(int logsId) {
		this.logsId = logsId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

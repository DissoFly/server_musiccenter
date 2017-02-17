package com.dissofly.musicplayer.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@MappedSuperclass
public class DateRecord {
	Date createDate;
	Date editDate;
	
	@Column(updatable=false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	
	@PreUpdate
	public void onPreUpdate(){
		editDate = new Date();
	}
	
	@PrePersist
	public void onPrePersist(){
		createDate = new Date();
		editDate = new Date();
	}
}

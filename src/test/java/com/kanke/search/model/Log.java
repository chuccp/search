package com.kanke.search.model;

import java.util.Date;

import com.kanke.search.annotation.StoreField;
import com.kanke.search.annotation.StoreFieldId;

public class Log {
	
	@StoreFieldId
	private String logId;
	
	@StoreField(value = "userId",isSort = true)
	private String userId;
	
	@StoreField(value = "videoId",isSort = true)
	private  String videoId;
	@StoreField(value = "hour",isSort = true)
	private Integer hour;
	
	private Date time;
	
	private Long watch;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getWatch() {
		return watch;
	}

	public void setWatch(Long watch) {
		this.watch = watch;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

}

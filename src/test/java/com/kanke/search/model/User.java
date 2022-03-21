package com.kanke.search.model;

import com.kanke.search.annotation.StoreField;
import com.kanke.search.annotation.StoreFieldId;

public class User {
	
	@StoreFieldId
	@StoreField(value = "userId",isSort = true)
	private String userId;
	
	@StoreField(value = "userName",isSort = true)
	private String userName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}

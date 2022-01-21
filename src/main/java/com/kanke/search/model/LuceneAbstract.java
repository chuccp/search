package com.kanke.search.model;

import java.util.Date;

import com.kanke.search.annotation.StoreFieldId;

public class LuceneAbstract {
	
	@StoreFieldId("id")
	private Long Id;

	private Date createTime;

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		if (createTime != null) {
			return createTime;
		}

		return new Date();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

}

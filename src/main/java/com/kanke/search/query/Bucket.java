package com.kanke.search.query;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public abstract class Bucket {
	
	private int groupId;
	
	
	public Bucket(int groupId) {
		this.groupId = groupId;
	}


	public int getGroupId() {
		return groupId;
	}


	public abstract String getStoreName(int num);



	public abstract GroupValue getFieldValue(String name);
	
	
	
	public abstract TermValue getTermValue();
	
	
	public abstract List<Document> getDocuments() throws IOException;


	public  Long getAmouts(String name) {
		return this.getFieldValue(name).getValue();
		
	}


}

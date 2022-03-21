package com.kanke.search.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Query;

import com.kanke.search.query.GroupIndexBuilders.Join;

public class GroupIndex {
	
	private Query query; 
	
	
	public GroupIndex(String index) {
		Index = index;
	}

	private String Index;
	

	public String getIndex() {
		return Index;
	}

	public void setIndex(String index) {
		Index = index;
	}
	private List<Join>  joinList =  new ArrayList<Join>();
	public	List<Join> joinList(){
		return joinList;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	} 
}

package com.kanke.search.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Query;

public class GroupIndexBuilders {

	public static GroupIndexBuilder index(String index, Query query) {
		GroupIndexBuilder groupIndexBuilder = new GroupIndexBuilder(index);
		groupIndexBuilder.setQuery(query);
		return groupIndexBuilder;
	}
	
	public static GroupIndexBuilder index(String index) {

		return new GroupIndexBuilder(index);
	}

	public static class GroupIndexBuilder {
		
		private Query query; 

		private String mainIndex;
		
		private List<Join> joinList = new ArrayList<>();
		
		
		private Map<String,Join> joinMap= new HashMap<>();
		

		private GroupIndexBuilder(String mainIndex) {
			this.mainIndex = mainIndex;
		}

		public Join Join(String index, String filedName,Query query) {
			Join join = new Join(index, filedName, query, this);
			joinMap.put(index, join);
			return join;
		}
		public Join Join(String index, String filedName) {
			Join join = new Join(index, filedName, null, this);
			joinMap.put(index, join);
			return join;
		}

		public Query getQuery() {
			return query;
		}

		public void setQuery(Query query) {
			this.query = query;
		}
		
	
		public GroupIndex build() {
			GroupIndex groupIndex = new GroupIndex(mainIndex);
			groupIndex.joinList().addAll(this.joinList);
			groupIndex.setQuery(query);
			return groupIndex;
		}

	}

	public static class Join {
		
		private String index;
		private String filedName;
		private Query query;
		private GroupIndexBuilder groupIndexBuilder;
		private String onIndex;
		private String onfiledName;
		
		
		private List<Join> joinList = new ArrayList<>();
		
		
		public List<Join> nextJoin(){
			return joinList;
		}
		
		
		public Join(String index, String filedName, Query query, GroupIndexBuilder groupIndexBuilder) {
			this.index = index;
			this.filedName = filedName;
			this.query = query;
			this.groupIndexBuilder = groupIndexBuilder;
		}
		public GroupIndexBuilder on(String index, String filedName) {
			this.onIndex = index;
			this.onfiledName = filedName;
			if(StringUtils.equals(index, groupIndexBuilder.mainIndex)) {
				groupIndexBuilder.joinList.add(this);
			}else {
				groupIndexBuilder.joinMap.get(index).joinList.add(this);
			}
			return groupIndexBuilder;
		}
		public GroupIndexBuilder on(String filedName) {
			return on(this.groupIndexBuilder.mainIndex, filedName);
		}
		
		public String getIndex() {
			return index;
		}
		public void setIndex(String index) {
			this.index = index;
		}
		public String getFiledName() {
			return filedName;
		}
		public void setFiledName(String filedName) {
			this.filedName = filedName;
		}
		public Query getQuery() {
			return query;
		}
		public void setQuery(Query query) {
			this.query = query;
		}
		public String getOnIndex() {
			return onIndex;
		}
		public void setOnIndex(String onIndex) {
			this.onIndex = onIndex;
		}
		public String getOnfiledName() {
			return onfiledName;
		}
		public void setOnfiledName(String onfiledName) {
			this.onfiledName = onfiledName;
		}
	}

}

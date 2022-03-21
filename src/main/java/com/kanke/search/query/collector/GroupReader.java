package com.kanke.search.query.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Query;

import com.kanke.search.query.Group;
import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.GroupField;
import com.kanke.search.query.GroupIndex;
import com.kanke.search.query.GroupIndexBuilders.Join;

public class GroupReader {
	
	private GroupIndex groupIndex;
	
	private Group group;

	public GroupReader(GroupIndex groupIndex, Group group) {
		this.groupIndex = groupIndex;
		this.group = group;
	}
	
	private Map<String,String[]> sortNamesMap = new HashMap<>();
	
	

	public List<Report> getReports() {
		List<Report> list = group.getReports();
		return list;
	}
	
	
	
	public String[] getMainSortNames() {
		return this.getSortNames(groupIndex.getIndex());
	} 
	
	public List<Join> getJoin() {
		return groupIndex.joinList();
	} 
	
	public Query getMainQuery() {
		return groupIndex.getQuery();
	}
	
	public String getMainIndex() {
		return groupIndex.getIndex();
	}
	
	public GroupField[] getGroupFields() {
		return this.group.getGroupFields();
	}
	
	public GroupField getGroupFields(String index) {
		return group.getGroupField(index);
		
	}
	
	
	
	public String[] getSortNames(String index) {
		if(sortNamesMap.containsKey(index)) {
			return sortNamesMap.get(index);
		}
		
		List<String> list = new ArrayList<>();
		String[] stores = this.group.getGroupField(index).getStoreNames();
		for(String store:stores) {
			list.add(store);
		}
		List<Join>  jlist = this.groupIndex.joinList();
		for(Join join:jlist) {
			String oFilename = join.getOnfiledName();
			if(!contains(list, oFilename)) {
				list.add(oFilename);
			}
		}
		String[] sorts =  list.toArray(new String[] {});
		sortNamesMap.put(index, sorts);
		return sorts;
	}
	
	
	private static boolean contains(List<String> list,String value) {
		for(String v:list) {
			if(StringUtils.equalsIgnoreCase(v, value)) {
				return true;
			}
		}
		return false;
	}
}

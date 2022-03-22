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
		List<Report> list = group.getReports();
		for(Report report:list) {
			GroupField groupField = report.getGroupField();
			if(groupField!=null) {
				if(StringUtils.isBlank(groupField.getIndex())) {
					groupField.setIndex(groupIndex.getIndex());
				}
			}
		}
		GroupField[]  gfs = this.group.getGroupFields();
		if(gfs!=null) {
			for(GroupField groupField:gfs) {
				if(groupField!=null) {
					if(StringUtils.isBlank(groupField.getIndex())) {
						groupField.setIndex(groupIndex.getIndex());
					}
				}
			}
		}
		this.group.initMap(); 
		
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
		GroupField groupField  = this.group.getGroupField(index);
		if(groupField!=null) {
			String[] stores = groupField.getStoreNames();
			for(String store:stores) {
				if(!contains(list,store)) {
					list.add(store);
				}
			}
		}
		List<Report> reports = group.getReports();
		for(Report report:reports) {
			if(report.getGroupField()!=null&&StringUtils.equalsIgnoreCase(index, report.getGroupField().getIndex())) {
				GroupField gf =  report.getGroupField();
				String[]  storeNames = gf.getStoreNames();
				for(String storeName:storeNames) {
					if(!contains(list,storeName)) {
						list.add(storeName);
					}
				}
			}
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

package com.kanke.search.query.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.BooleanClause.Occur;

import com.kanke.search.query.selector.Selector;

public class GroupBoolQuery extends GroupQuery {

	private List<Occur> occurList = new ArrayList<>();

	private List<GroupQuery> groupQueryList = new ArrayList<>();

	public GroupBoolQuery() {
		occurList.clear();
		groupQueryList.clear();
	}

	public GroupBoolQuery must(GroupQuery groupQuery) {
		groupQueryList.add(groupQuery);
		occurList.add(Occur.MUST);
		return this;
	}

	public GroupBoolQuery should(GroupQuery groupQuery) {
		groupQueryList.add(groupQuery);
		occurList.add(Occur.SHOULD);
		return this;
	}

	@Override
	public boolean equals(int groupId, Map<String, Selector> selectorMap) {
		for (int i = 0; i < groupQueryList.size()-1; i++) {
			GroupQuery  groupQuery  = groupQueryList.get(i);
			boolean flag = groupQuery.equals(groupId, selectorMap);
			if(occurList.get(i).compareTo(Occur.SHOULD)==0) {
				if(flag) {
					return true;
				}
			}
			if(occurList.get(i).compareTo(Occur.MUST)==0) {
				if(!flag) {
					return false;
				}
			}
		}
		GroupQuery  groupQuery  = groupQueryList.get(groupQueryList.size()-1);
		return groupQuery.equals(groupId, selectorMap);
	}

}

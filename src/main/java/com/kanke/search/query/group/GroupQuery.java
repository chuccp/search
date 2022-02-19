package com.kanke.search.query.group;

import java.util.Map;

import com.kanke.search.query.selector.Selector;

public abstract class GroupQuery {
	
	 public abstract boolean equals(int groupId,Map<String,Selector> selectorMap);

}

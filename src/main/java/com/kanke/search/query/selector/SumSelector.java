package com.kanke.search.query.selector;

import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public class SumSelector extends Selector {
	
	public SumSelector(Report report) {
		super(report);
	}

	@Override
	public void collect(int groupId, TermValue termValue) {
		
	}

	@Override
	public GroupValue getGroupValue(Integer groupId) {
		return null;
	}




}

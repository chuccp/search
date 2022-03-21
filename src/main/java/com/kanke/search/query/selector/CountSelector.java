package com.kanke.search.query.selector;

import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.collector.TermValue;

public class CountSelector extends Selector{
	
	public CountSelector(Report report) {
		super(report);
	}


	@Override
	public void collect(int groupId, TermValue termValue) {
		
	}

}

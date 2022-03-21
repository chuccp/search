package com.kanke.search.query.selector;

import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public abstract class Selector {

	private Report report;
	
	public abstract GroupValue getGroupValue(Integer groupId);
	
	
	public Selector(Report report) {
		this.report = report;
	}

	public abstract  void collect(int groupId,TermValue termValue);


	public Report getReport() {
		return report;
	}

	public boolean isReverse() {
		return this.report.isReverse();
	} 

	public boolean isOrder() {
		return this.report.isOrder();
	}
	
	
	public String getFieldName() {
		return report.getAliasName();
	}
}

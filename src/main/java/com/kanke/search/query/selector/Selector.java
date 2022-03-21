package com.kanke.search.query.selector;

import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.collector.TermValue;

public abstract class Selector {

	private Report report;
	
	
	
	public Selector(Report report) {
		this.report = report;
	}



	public abstract  void collect(int groupId,TermValue termValue);


	public Report getReport() {
		return report;
	}

	
}

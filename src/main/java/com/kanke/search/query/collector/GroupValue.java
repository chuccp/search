package com.kanke.search.query.collector;

public class GroupValue {
	
	
	
	public GroupValue(TermValue termValue, long count) {
		this.termValue = termValue;
		this.count = count;
	}

	private TermValue  termValue;
	
	private long count;

	public TermValue getTermValue() {
		return termValue;
	}

	public void setTermValue(TermValue termValue) {
		this.termValue = termValue;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	public void add() {
		this.count++;
	}
	
}

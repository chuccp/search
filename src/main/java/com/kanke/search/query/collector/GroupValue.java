package com.kanke.search.query.collector;

public class GroupValue {

	public GroupValue(TermValue termValue) {
		this.termValue = termValue;
		this.value = 0;
	}

	private TermValue termValue;

	private long value;

	public TermValue getTermValue() {
		return termValue;
	}

	public void setTermValue(TermValue termValue) {
		this.termValue = termValue;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public void count() {
		this.value++;
	}

	public void sum(long num) {
		this.value = this.value + num;
	}

}

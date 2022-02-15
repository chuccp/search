package com.kanke.search.query;

import java.util.ArrayList;
import java.util.List;

import com.kanke.search.query.collector.TermCollector;

public class GroupResponse {
	
	private TermCollector  termCollector;
	
	
	
	
	public GroupResponse(TermCollector termCollector) {
		this.termCollector = termCollector;
	}



	private List<String> amountName = new ArrayList<String>();
	
	
	private List<Bucket> buckets;


	public List<String> getAmountName() {
		return amountName;
	}


	public void addAmountName(String amountName) {
		this.amountName.add(amountName);
	}


	public List<Bucket> getBuckets() {
		return buckets;
	}


	public void setBuckets(List<Bucket> buckets) {
		this.buckets = buckets;
	}



	public void setAmountName(List<String> amountName) {
		this.amountName = amountName;
	}
	
	
	

}

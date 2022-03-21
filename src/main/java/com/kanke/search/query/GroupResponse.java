package com.kanke.search.query;

import java.util.ArrayList;
import java.util.List;

import com.kanke.search.query.selector.TermSelector;

public class GroupResponse {

	private Pageable pageable;
	
	private TermSelector termSelector;
	

	public GroupResponse(Pageable pageable,TermSelector termSelector) {
		this.pageable = pageable;
		this.termSelector = termSelector;

	}
	

	public void exec() {
		
		
		
		
	}


	private List<String> amountName = new ArrayList<String>();

	private List<Bucket> buckets = new ArrayList<>();

	public List<String> getAmountName() {
		return amountName;
	}

	public void addAmountName(String amountName) {
		this.amountName.add(amountName);
	}

	public List<Bucket> getBuckets() {
		return buckets;
	}

	public void setAmountName(List<String> amountName) {
		this.amountName = amountName;
	}

	
	
}

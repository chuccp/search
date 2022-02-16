package com.kanke.search.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.lucene.util.NumericUtils;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermCollector;

public class GroupResponse {
	
	private TermCollector  termCollector;
	
	
	private boolean reverse = false;
	
	private Pageable pageable;
	
	
	public GroupResponse(TermCollector termCollector,boolean reverse,Pageable pageable) {
		this.termCollector = termCollector;
		this.reverse = reverse;
		this.pageable = pageable;
		this.readValue();
		
	}


	private void readValue() {
		Map<Integer, GroupValue> mapValue = termCollector.getGroupValue();
		
		
		
		
		List<GroupValue>   list = new ArrayList<>(mapValue.values());
		
		
		
		
		
		
		if(this.reverse) {
			Collections.sort(list, (v1,v2)->NumberUtils.compare(v2.getCount(), v1.getCount()));
		}else {
			Collections.sort(list, (v1,v2)->NumberUtils.compare(v1.getCount(), v2.getCount()));
		}
		
		int num = list.size();
		if(num>pageable.getOffset()) {
			int rSize =  num - pageable.getOffset();
			if(pageable.getLimit()>rSize) {
				pageable.setLimit(rSize);
			}
			list = list.subList(pageable.getOffset(), pageable.getLimit());
			list.forEach((v)->{
				Bucket bucket = new Bucket(v);
				buckets.add(bucket);
			});
		}
		
	
		
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

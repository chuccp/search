package com.kanke.search.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;
import com.kanke.search.query.selector.TermSelector;

public class GroupResponse {

	private Pageable pageable;
	
	private TermSelector termSelector;
	

	public GroupResponse(Pageable pageable,TermSelector termSelector) {
		this.pageable = pageable;
		this.termSelector = termSelector;

	}
	

	public void exec() {
		List<Integer> groupIds = termSelector.groupIds();
		int fromIndex = pageable.getOffset();
		int toIndex = pageable.getOffset()+pageable.getLimit();
		List<Integer> ids = groupIds.subList(fromIndex,toIndex);
		
		
		
		for(Integer id:ids) {
			buckets.add(new Bucket(id) {
				
				@Override
				public TermValue getTermValue() {
					return termSelector.getTermValue(this.getGroupId());
				}
				
				@Override
				public String getStoreName(int num) {
					return termSelector.getTermValue(this.getGroupId()).getValue(null);
				}
				
				@Override
				public GroupValue getFieldValue(String name) {
					return termSelector.getSelector(name).getGroupValue(this.getGroupId());
				}
				
				@Override
				public List<Document> getDocuments() throws IOException {
					return null;
				}
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

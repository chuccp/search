package com.kanke.search.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.kanke.search.query.collector.AllGroupCollector;
import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;
import com.kanke.search.query.selector.TermSelector;

public class GroupResponse {

	private AllGroupCollector allGroupCollector;


	private Pageable pageable;

	public GroupResponse(AllGroupCollector allGroupCollector, Pageable pageable) {
		this.allGroupCollector = allGroupCollector;
		this.pageable = pageable;
		this.readValue();

	}

	private void readValue() {

		TermSelector termSelector = this.allGroupCollector.getTermSelector();
		
		
		Map<Integer, GroupValue> mapValue = termSelector.getMapValue();
		List<Integer> list = new ArrayList<>(mapValue.keySet());
		if (termSelector.isReverse()) {
			Collections.sort(list,
					(v1, v2) -> NumberUtils.compare(mapValue.get(v1).getValue(), mapValue.get(v2).getValue()));
		} else {
			Collections.sort(list,
					(v1, v2) -> NumberUtils.compare(mapValue.get(v2).getValue(), mapValue.get(v1).getValue()));
		}

		int num = list.size();
		if (num > pageable.getOffset()) {
			int rSize = num - pageable.getOffset();
			if (pageable.getLimit() > rSize) {
				pageable.setLimit(rSize);
			}
			list = list.subList(pageable.getOffset(), pageable.getLimit());
			list.forEach((v) -> {
				Bucket bucket = new Bucket(v) {

					@Override
					public String getStoreName(int num) {
						return termSelector.getStoreNames()[num];
					}

					@Override
					public TermValue getFieldValue(String name) {
						return GroupResponse.this.getTermValue(this.getGroupId(), name);
					}};
				buckets.add(bucket);
			});
		}
	}

	private TermValue getTermValue(int groupId,String groupName) {
		
		this.allGroupCollector.getSelectorMap().get(groupName);
		
		
		return null;
		
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

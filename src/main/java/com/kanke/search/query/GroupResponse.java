package com.kanke.search.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;
import com.kanke.search.query.selector.Selector;
import com.kanke.search.query.selector.TermSelector;
import com.kanke.search.util.GroupUtils;

public class GroupResponse {

	private Pageable pageable;
	
	private TermSelector termSelector;
	

	public GroupResponse(Pageable pageable,TermSelector termSelector) {
		this.pageable = pageable;
		this.termSelector = termSelector;

	}

	public void exec() {
		List<Selector> slist = new ArrayList<Selector>(termSelector.getSelectorList());
		List<Integer> groupIds = termSelector.groupIds();
		int num = groupIds.size();
		if (num > pageable.getOffset()) {
			int fromIndex = pageable.getOffset();
			int toIndex =  pageable.getOffset() +pageable.getLimit();
			if (toIndex > num) {
				toIndex = num;
			}
			slist.removeIf(v->(!v.isOrder()));
			if(!slist.isEmpty()) {
				Selector firstSelector = slist.get(0);
				if(slist.size()>1) {
					OrderSub orderSub = GroupUtils.orderOptimiz(groupIds,firstSelector, fromIndex, toIndex);
					groupIds = orderSub.getUlist();
					Collections.reverse(slist);
					for (Selector selector : slist) {
						if (selector.isReverse()) {
							Collections.sort(groupIds,(v1, v2) -> NumberUtils.compare(selector.getGroupValue(v2).getValue(), selector.getGroupValue(v1).getValue()));
						} else {
							Collections.sort(groupIds,(v1, v2) -> NumberUtils.compare(selector.getGroupValue(v1).getValue(), selector.getGroupValue(v2).getValue()));
						}
					}
					groupIds = orderSub.getPageList();
				}else {
					if (firstSelector.isReverse()) {
						Collections.sort(groupIds,(v1, v2) -> NumberUtils.compare(firstSelector.getGroupValue(v2).getValue(), firstSelector.getGroupValue(v1).getValue()));
					} else {
						Collections.sort(groupIds,(v1, v2) -> NumberUtils.compare(firstSelector.getGroupValue(v1).getValue(), firstSelector.getGroupValue(v2).getValue()));
					}
					groupIds = groupIds.subList(fromIndex,toIndex);
				}
			}else {
				groupIds = groupIds.subList(fromIndex,toIndex);
			}
		}
		for (Integer groupId : groupIds) {
			buckets.add(new Bucket(groupId) {
				
				@Override
				public TermValue getTermValue() {
					return termSelector.getTermValue(this.getGroupId());
				}
				
				@Override
				public String getStoreName(int num) {
					return termSelector.getTermValue(this.getGroupId()).getValue(num);
				}
				
				@Override
				public GroupValue getFieldValue(String name) {
					return termSelector.getSelector(name).getGroupValue(this.getGroupId());
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

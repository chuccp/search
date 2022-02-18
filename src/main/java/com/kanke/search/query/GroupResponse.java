package com.kanke.search.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.kanke.search.query.collector.AllGroupCollector;
import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;
import com.kanke.search.query.selector.Selector;
import com.kanke.search.query.selector.TermSelector;
import com.kanke.search.util.GroupUtils;

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
		Map<String, Selector> allselector = this.allGroupCollector.getAllSelectorMap();

		List<Selector> slist = new ArrayList<Selector>(allselector.values());
		
		
		
		
		
		int num = list.size();
		
		
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
					OrderSub orderSub = GroupUtils.orderOptimiz(list, mapValue, firstSelector, fromIndex, toIndex);
					list = orderSub.getUlist();
					Collections.reverse(slist);
					for (Selector selector : slist) {
						if (selector.isReverse()) {
							Collections.sort(list,(v1, v2) -> NumberUtils.compare(selector.get(v2).getValue(), selector.get(v1).getValue()));
						} else {
							Collections.sort(list,(v1, v2) -> NumberUtils.compare(selector.get(v1).getValue(), selector.get(v2).getValue()));
						}
					}
					list = orderSub.getPageList();
				}else {
					if (firstSelector.isReverse()) {
						Collections.sort(list,(v1, v2) -> NumberUtils.compare(firstSelector.get(v2).getValue(), firstSelector.get(v1).getValue()));
					} else {
						Collections.sort(list,(v1, v2) -> NumberUtils.compare(firstSelector.get(v1).getValue(), firstSelector.get(v2).getValue()));
					}
					list = list.subList(fromIndex,toIndex);
				}
			}else {
				list = list.subList(fromIndex,toIndex);
			}
		}
		for (Integer groupId : list) {
			Bucket bucket = new Bucket(groupId) {
				@Override
				public String getStoreName(int num) {
					return termSelector.getStoreNames()[num];
				}
				
				@Override
				public GroupValue getFieldValue(String name) {
					return GroupResponse.this.getTermValue(this.getGroupId(), name);
				}
				
				@Override
				public TermValue getTermValue() {
					return termSelector.getTermValue(this.getGroupId());
				}
			};
			buckets.add(bucket);
		}
	}

	private GroupValue getTermValue(int groupId, String groupName) {
		return this.allGroupCollector.getAllSelectorMap().get(groupName).get(groupId);
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

package com.kanke.search.query.selector;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.collector.BytesRefValue;
import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public class SumSelector extends Selector {
	
	private Map<Integer, Sum> termMap = new LinkedHashMap<>();
	
	private String storeName;
	
	public SumSelector(Report report) {
		super(report);
		this.storeName = report.getGroupField().getStoreNames()[0];
	}

	@Override
	public void collect(int groupId, TermValue termValue) {
		BytesRefValue bytesRefValue = termValue.get(storeName);
		if(!termMap.containsKey(groupId)) {
			termMap.put(groupId, new Sum());
		}
		termMap.get(groupId).add(bytesRefValue.toNumber());
	}


	class Sum {
		private long num;
		public void add(long num) {
			this.num = this.num+num;
		}
	}
	@Override
	public GroupValue getGroupValue(Integer groupId) {
		GroupValue gv = new GroupValue();
		gv.setValue(termMap.get(groupId).num);
		return gv;
	}


}

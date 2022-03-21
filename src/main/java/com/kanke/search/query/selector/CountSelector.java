package com.kanke.search.query.selector;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.collector.TermValue;

public class CountSelector extends Selector {

	private Map<Integer, Count> termMap = new LinkedHashMap<>();

	public CountSelector(Report report) {
		super(report);
	}

	@Override
	public void collect(int groupId, TermValue termValue) {
		if (!termMap.containsKey(groupId)) {
			termMap.put(groupId, new Count());
		}
		termMap.get(groupId).add();
	}

	class Count {
		private int num;

		public void add() {
			num++;
		}
	}

	@Override
	public GroupValue getGroupValue(Integer groupId) {
		GroupValue gv = new GroupValue();
		gv.setValue(termMap.get(groupId).num);
		return gv;
	}
}

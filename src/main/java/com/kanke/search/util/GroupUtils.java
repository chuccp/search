package com.kanke.search.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.kanke.search.query.OrderSub;
import com.kanke.search.query.collector.GroupValue;
import com.kanke.search.query.selector.Selector;

public class GroupUtils {
	
	public static OrderSub orderOptimiz (List<Integer> list,Map<Integer, GroupValue> mapValue,Selector selector,int fromIndex, int toIndex) {
		if (selector.isReverse()) {
			Collections.sort(list,(v1, v2) -> NumberUtils.compare(selector.get(v2).getValue(), selector.get(v1).getValue()));
		} else {
			Collections.sort(list,(v1, v2) -> NumberUtils.compare(selector.get(v1).getValue(), selector.get(v2).getValue()));
		}
		int fromIndex1 = fromIndex;
		int toIndex1 = toIndex;
		if(fromIndex>0) {
			long lite =mapValue.get(list.get(fromIndex)).getValue();
			while(true) {
				fromIndex1 = fromIndex1-1;
				long v =  mapValue.get(list.get(fromIndex1)).getValue();
				if(!(v==lite)) {
					fromIndex1++;
					break;
				}
			}
		}
		int size = list.size();
		if(size>toIndex) {
			long pig =mapValue.get(list.get(toIndex)).getValue();
			while(true) {
				toIndex1 = toIndex1+1;
				long v = mapValue.get(list.get(toIndex1)).getValue();
				if(!(v==pig)) {
					toIndex1--;
					break;
				}
			}
		}
		OrderSub os = new OrderSub(fromIndex,toIndex);
		os.setUlist(list.subList(fromIndex1, toIndex1));
		int f = fromIndex - fromIndex1;
		int t = toIndex1 - toIndex;
		os.setLowMore(f);
		os.setUpMore(t);
		return os;
	}
}

package com.kanke.search.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

public class GroupUtils {
	
	
	static class Value{
		
		int a;
		int b;
		int c;
		int d;
		public Value(int a, int b, int c, int d) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}
	}
	
	
	private static Map<Integer,Value> vMap = new LinkedHashMap<>();
	
	
	public static void main(String[] args) {
		
		vMap.put(1, new Value(1, 6, 3, 3));
		vMap.put(2, new Value(2, 6, 3, 2));
		vMap.put(3, new Value(6, 5, 2, 1));
		vMap.put(4, new Value(6, 6, 1, 2));
		vMap.put(5, new Value(6, 7, 2, 3));
		vMap.put(6, new Value(6, 6, 1, 3));
		vMap.put(7, new Value(7, 5, 2, 3));
		vMap.put(8, new Value(8, 4, 2, 1));
		vMap.put(9, new Value(9, 5, 1, 2));
		vMap.put(10, new Value(9, 3, 1, 3));
		vMap.put(11, new Value(9, 6, 3, 1));
		vMap.put(12, new Value(9, 4, 1, 2));
		vMap.put(13, new Value(12, 4, 2, 2));
		vMap.put(14, new Value(14, 5, 3, 3));
		vMap.put(15, new Value(15, 6, 2, 2));
		vMap.put(16, new Value(16, 7, 1, 2));
		vMap.put(17, new Value(17, 8, 2, 2));
		vMap.put(18, new Value(18, 4, 3, 2));
		vMap.put(19, new Value(19, 4, 4, 2));
		vMap.put(20, new Value(20, 5, 4, 2));
		vMap.put(21, new Value(21, 6, 3, 1));
		vMap.put(22, new Value(22, 6, 2, 1));
		vMap.put(23, new Value(23, 6, 3, 1));
		vMap.put(24, new Value(24, 5, 2, 2));
		vMap.put(25, new Value(25, 5, 1, 2));

		
		List<Integer> list = new ArrayList<>(vMap.keySet());
		
		Collections.sort(list,(v1,v2)->NumberUtils.compare(vMap.get(v1).a, vMap.get(v2).a));
		
		int start = 5;
		int end = 10;
		
		List<Integer> vlist = list.subList(start, end);

		for(Integer v:vlist) {
			System.out.println(v);
		}
		//往上找
		int lite = vMap.get(list.get(start)).a;
		int start1 = start;
		while(true) {
			start1 = start1-1;
			int v =  vMap.get(list.get(start1)).a;
			if(!(v==lite)) {
				start1++;
				break;
			}
		}
		
		List<Integer> uplist = list.subList(start1, start);
		
		
		
		
		
		//往下找
		
		int pig = vMap.get(list.get(end)).a;
		int end1 = end;
		while(true) {
			end1 = end1+1;
			int v = vMap.get(list.get(end1)).a;
			if(!(v==pig)) {
				end1--;
				break;
			}
		}
		
		List<Integer> downlist = list.subList(end, end1);
		
		
		
		
		
	}

	
	
}

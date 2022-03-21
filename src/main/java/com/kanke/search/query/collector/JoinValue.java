package com.kanke.search.query.collector;

import java.util.HashSet;
import java.util.Set;

import com.kanke.search.query.GroupIndexBuilders.Join;

public class JoinValue {
	
	
	public JoinValue(Join join) {
		this.join = join;
	}


	private	Set<BytesRefValue> nouse = new HashSet<BytesRefValue>();
	
	
	private Set<BytesRefValue> use = new HashSet<BytesRefValue>();
	
	
	private Join join;


	public Join getJoin() {
		return join;
	}


	public void setJoin(Join join) {
		this.join = join;
	}


	public void add(BytesRefValue bytesRefValue) {
		if(!use.contains(bytesRefValue)) {
			nouse.add(bytesRefValue);
		}
	}
}

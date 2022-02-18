package com.kanke.search.query;

import java.util.List;

public class OrderSub {

	List<Integer> ulist;

	private int size;

	public OrderSub(int fromIndex, int toIndex) {
		this.size = toIndex - fromIndex;
	}

	private int lowMore;

	private int upMore;

	public List<Integer> getUlist() {
		return ulist;
	}

	public void setUlist(List<Integer> ulist) {
		this.ulist = ulist;
	}

	public int getLowMore() {
		return lowMore;
	}

	public void setLowMore(int lowMore) {
		this.lowMore = lowMore;
	}

	public int getUpMore() {
		return upMore;
	}

	public void setUpMore(int upMore) {
		this.upMore = upMore;
	}

	public List<Integer> getPageList() {
		return ulist.subList(lowMore, lowMore + size);
	}

}

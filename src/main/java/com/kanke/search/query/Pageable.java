package com.kanke.search.query;

public class Pageable {

	private int offset;

	private int limit;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Pageable(int offset, int limit) {
		super();
		this.offset = offset;
		this.limit = limit;
	}

	public static Pageable page(int offset, int limit) {

		return new Pageable(offset, limit);
	}

	public static Pageable page(int limit) {

		return Pageable.page(0, limit);
	}

}

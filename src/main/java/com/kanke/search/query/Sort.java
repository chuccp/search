package com.kanke.search.query;

public class Sort {
	
	private String fileName;
	
	private boolean reverse = false;
	
	public static Sort by(String fileName) {
		Sort sort = 	new Sort();
		sort.fileName = fileName;
		return sort;
	}
	public String getFileName() {
		return fileName;
	}

	
	public boolean isReverse() {
		return reverse;
	}
	public Sort asc() {
		this.reverse = false;
		return this;
	}
	public Sort desc() {
		this.reverse = true;
		return this;
	}
}

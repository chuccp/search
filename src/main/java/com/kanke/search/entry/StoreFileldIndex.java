package com.kanke.search.entry;

import java.util.Objects;

import com.kanke.search.annotation.StoreIndex;

@StoreIndex("StoreFileldIndex")
public class StoreFileldIndex {
	
	
	private String className;
	
	private String indexName;
	
	
	private String filedName;
	
	private String sortName;
	private String storeName;
	
	private String genericType;
	
	private boolean sort;
	
	private String id;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}


	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getGenericType() {
		return genericType;
	}

	public void setGenericType(String genericType) {
		this.genericType = genericType;
	}

	public boolean isSort() {
		return sort;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(className, filedName, genericType, id, indexName, sort, sortName, storeName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreFileldIndex other = (StoreFileldIndex) obj;
		return Objects.equals(className, other.className) && Objects.equals(filedName, other.filedName)
				&& Objects.equals(genericType, other.genericType) && Objects.equals(id, other.id)
				&& Objects.equals(indexName, other.indexName) && sort == other.sort
				&& Objects.equals(sortName, other.sortName) && Objects.equals(storeName, other.storeName);
	}
	
	
	

}

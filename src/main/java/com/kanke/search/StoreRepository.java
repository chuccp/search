package com.kanke.search;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Query;

import com.kanke.search.annotation.StoreIndex;

public abstract class StoreRepository<T> {

	@SuppressWarnings("rawtypes")
	private static Map<Class<? extends StoreRepository>, Class<?>> classMap = new HashMap<>();

	public abstract StoreTemplate getStoreTemplate();

	public void insertOrUpdate(T t) {
		try {
			this.getStoreTemplate().writeOrUpdate(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertOrUpdate(List<T> t) {
		try {
			this.getStoreTemplate().writeOrUpdate(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<T> search(Query query, int top) {
		String index = this.getIndex();
		return this.getStoreTemplate().search(index, query, top, this.getEntryClass());
	}


	public List<T> search(Query query, com.kanke.search.query.Sort sort, int top) {
		String index = this.getIndex();
		return this.getStoreTemplate().search(index,query,sort,top, this.getEntryClass());
	}

	public void delete(Query query) throws IOException {
		this.getStoreTemplate().delete(getIndex(), query);
	}

	public void deleteIndex() throws IOException {
		this.getStoreTemplate().deleteIndex(getIndex());
	}

//	public  List<T> group(Group group, Query query,Pageable pageable){
//		return this.getStoreTemplate().group(this.getIndex(), group, query,pageable);
//	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntryClass() {
		@SuppressWarnings("rawtypes")
		Class<? extends StoreRepository> thizCls = this.getClass();
		if (classMap.containsKey(thizCls)) {
			return (Class<T>) classMap.get(thizCls);
		}
		Type genericArrayType = thizCls.getGenericSuperclass();
		String name = genericArrayType.getTypeName();
		String clsname = StringUtils.substringBefore(name, "<");
		if (StringUtils.equalsIgnoreCase(clsname, StoreRepository.class.getCanonicalName())) {
			String className = StringUtils.substringBetween(name, "<", ">");
			try {
				Class<T> cls = (Class<T>) ClassUtils.getClass(className);
				classMap.put(thizCls, cls);
				return cls;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public String getIndex() {
		StoreIndex storeIndex = this.getEntryClass().getAnnotation(StoreIndex.class);
		return storeIndex.value();
	}
}

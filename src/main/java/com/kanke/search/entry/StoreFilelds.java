package com.kanke.search.entry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.kanke.search.annotation.StoreField;
import com.kanke.search.annotation.StoreFieldId;
import com.kanke.search.annotation.StoreIgnoreField;
import com.kanke.search.annotation.StoreIndex;

public class StoreFilelds {

	private Class<?> storeClazz;

	private StoreFileld idField;

	private List<StoreFileld> fields = new ArrayList<StoreFileld>();
	
	private Map<String,StoreFileld> storeFileldMap = new HashMap<>();

	private String storeIndex;


	public Class<?> getStoreClazz() {
		return storeClazz;
	}

	public void setStoreClazz(Class<?> storeClazz) {
		this.storeClazz = storeClazz;
	}

	public StoreFileld getIdField() {
		return idField;
	}

	public void setIdField(StoreFileld idField) {
		this.idField = idField;
	}

	public List<StoreFileld> getFields() {
		return fields;
	}

	public void setFields(List<StoreFileld> fields) {
		this.fields = fields;
	}

	public void addFields(StoreFileld storeFileld) {
		fields.add(storeFileld);
		storeFileldMap.put(storeFileld.getStoreName(), storeFileld);
	}
	
	
	public String getStoreIndex() {
		return storeIndex;
	}

	public void setStoreIndex(String storeIndex) {
		this.storeIndex = storeIndex;
	}
	
	public StoreFileld getStoreFileld(String storeName) {
		return storeFileldMap.get(storeName);
	}

	public static StoreFilelds get(Class<?> cls) {
		Field[] fields = FieldUtils.getAllFields(cls);
		StoreFilelds storeFilelds = new StoreFilelds();
		storeFilelds.setStoreClazz(cls);
		StoreIndex storeIndex = cls.getAnnotation(StoreIndex.class);
		if(storeIndex!=null) {
			storeFilelds.setStoreIndex(storeIndex.value());
		}else {
			storeFilelds.setStoreIndex(cls.getSimpleName());
		}
		
		for (Field field : fields) {
			StoreIgnoreField storeIgnoreField = field.getAnnotation(StoreIgnoreField.class);
			if (storeIgnoreField == null) {
				StoreFileld storeFileld = new StoreFileld();
				StoreField storeField = field.getAnnotation(StoreField.class);
				String fieldName = field.getName();
				storeFileld.setFieldName(fieldName);
				storeFileld.setField(field);
				if (storeField != null) {
					String v=storeField.value();
					if(StringUtils.isBlank(v)) {
						v = storeField.name();
					}
					storeFileld.setStoreName(storeField.value());
					storeFileld.setSort(storeField.isSort());
				} else {
					storeFileld.setStoreName(fieldName);
					storeFileld.setSort(false);
				}
				StoreFieldId storeFieldId = field.getAnnotation(StoreFieldId.class);
				if (storeFieldId != null) {
					storeFileld.setId(true);
					storeFilelds.idField = storeFileld;
				}
				storeFilelds.addFields(storeFileld);
			}
		}
		return storeFilelds;
	}


}

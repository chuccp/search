package com.kanke.search.util;

import java.lang.reflect.Type;
import java.util.Date;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FloatDocValuesField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.util.BytesRef;

import com.kanke.search.entry.StoreFileld;
import com.kanke.search.entry.StoreFilelds;

public class DocumentUtil {

	public static <T> Document toDoc(T t, StoreFilelds storeFilelds) {
		Document document = new Document();
		for (StoreFileld field : storeFilelds.getFields()) {
			try {
				Object obj = FieldUtils.readField(field.getField(), t, true);
				if (obj != null) {
					if (field.isSort()) {
						if (obj instanceof String) {
							SortedDocValuesField sortedDocValuesField = new SortedDocValuesField(field.getStoreName(),new BytesRef((String) obj));
							document.add(sortedDocValuesField);
						} else if (obj instanceof Long) {
							document.add(new NumericDocValuesField(field.getStoreName(), (Long) obj));
						} else if (obj instanceof Date) {
							document.add(new NumericDocValuesField(field.getStoreName(), ((Date) obj).getTime()));
						} else if (obj instanceof Integer) {
							document.add(new NumericDocValuesField(field.getStoreName(), (Integer) obj));
						}else if (obj instanceof Boolean) {
							document.add(new NumericDocValuesField(field.getStoreName(),  ((Boolean) obj) == true ? 1 : 0));
						}else if (obj instanceof Float) {
							document.add(new FloatDocValuesField(field.getStoreName(),  (Float)obj));
						}
					}
					String fieldName = field.getStoreName();
					if (obj instanceof String) {
						StringField stringField = new StringField(fieldName, (String) obj,org.apache.lucene.document.Field.Store.YES);
						document.add(stringField);
					} else if (obj instanceof Long) {
						StoredField storedField = new StoredField(fieldName, (Long) obj);
						document.add(storedField);
					} else if (obj instanceof Date) {
						StoredField storedField = new StoredField(fieldName, ((Date) obj).getTime());
						document.add(storedField);
					} else if (obj instanceof Integer) {
						StoredField storedField = new StoredField(fieldName, (Integer) obj);
						document.add(storedField);
					} else if (obj instanceof Boolean) {
						StoredField storedField = new StoredField(fieldName, ((Boolean) obj) == true ? 1 : 0);
						document.add(storedField);
					}else if (obj instanceof Float) {
						StoredField storedField = new StoredField(fieldName, (Float) obj);
						document.add(storedField);
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return document;
	}

	public static Object toEntry(Document doc, StoreFilelds storeFilelds) {
		try {

			Object t = storeFilelds.getStoreClazz().newInstance();
			for (StoreFileld field : storeFilelds.getFields()) {
				String fieldName = field.getStoreName();
				IndexableField indexableField = doc.getField(fieldName);
				if (indexableField != null) {
					Type type = field.getField().getGenericType();
					if (type == String.class) {
						FieldUtils.writeField(field.getField(), t, indexableField.stringValue(), true);
					} else if (type == Long.class) {
						FieldUtils.writeField(field.getField(), t, indexableField.numericValue().longValue(), true);
					} else if (type == Date.class) {
						FieldUtils.writeField(field.getField(), t, new Date(indexableField.numericValue().longValue()),true);
					} else if (type == Integer.class) {
						FieldUtils.writeField(field.getField(), t, indexableField.numericValue().intValue(), true);
					}else if (type == Boolean.class) {
						FieldUtils.writeField(field.getField(), t, indexableField.numericValue().intValue()==1, true);
					}else if (type == Float.class) {
						FieldUtils.writeField(field.getField(), t, indexableField.numericValue().floatValue(), true);
					}
				}
			}
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

package com.kanke.search.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexableField;

import com.kanke.search.annotation.StoreField;
import com.kanke.search.annotation.StoreFieldId;
import com.kanke.search.model.LuceneAbstract;

public class DocumentUtil {

	public static <T extends LuceneAbstract> Document toDoc(T t) {

		Document document = new Document();
		Class<? extends LuceneAbstract> cls = t.getClass();
		Field[] fields = FieldUtils.getAllFields(cls);

		for (Field field : fields) {
			try {
				Object obj = FieldUtils.readField(field, t, true);
				if (obj != null) {
					String fieldName = getFileName(field);

					if (obj instanceof String) {
						document.add(new StringField(fieldName, (String) obj, org.apache.lucene.document.Field.Store.YES));
					} else if (obj instanceof Long) {
						document.add(new NumericDocValuesField(fieldName, (Long) obj));
					} else if (obj instanceof Date) {
						document.add(new NumericDocValuesField(fieldName, ((Date) obj).getTime()));
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

	private static String getFileName(Field field) {
		String fieldName = "";
		StoreField storeField = field.getAnnotation(StoreField.class);
		if (storeField != null) {
			fieldName = storeField.value();
		} else {
			StoreFieldId storeFieldId = field.getAnnotation(StoreFieldId.class);
			if (storeFieldId != null) {
				fieldName = storeFieldId.value();
			}
			fieldName = field.getName();
		}
		return fieldName;
	}
	
	public static <T extends LuceneAbstract> T toEntry(Document doc, Class<T> clazz) {
		try {

			T t = clazz.newInstance();
			Field[] fields = FieldUtils.getAllFields(clazz);
			for (Field field : fields) {
				String fieldName = getFileName(field);
				IndexableField  indexableField  = doc.getField(fieldName);
				if(indexableField!=null) {
					Type type = field.getGenericType();
					if(type==String.class) {
						FieldUtils.writeField(field, t, indexableField.stringValue(), true);
					}else if (type==Long.class) {
						FieldUtils.writeField(field, t, indexableField.numericValue().longValue(), true);
					}else if (type==Date.class) {
						FieldUtils.writeField(field, t, new Date(indexableField.numericValue().longValue()), true);
					}
				}
			}
			return t;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

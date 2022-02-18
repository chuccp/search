package com.kanke.search.query;

import java.util.Date;

import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

public class QueryUtils {

	public static Query createQuery(String field, long value) {
		return NumericDocValuesField.newSlowRangeQuery(field, value, value);
	}

	public static Query createWildcardQuery(String field, String value) {
		Query query = new WildcardQuery(new Term(field, value));
		return query;
	}

	public static Query createTermQuery(String field, String value) {
		Query query = new TermQuery(new Term(field, value));
		return query;
	}

	public static Query createQuery(String field, boolean value) {
		int v = value ? 1 : 0;
		return NumericDocValuesField.newSlowRangeQuery(field, v, v);
	}

	public static Query createRangeQuery(String field, long lowerValue, long upperValue) {
		return NumericDocValuesField.newSlowRangeQuery(field, lowerValue, upperValue);
	}

	public static Query createRangeQuery(String field, Date lowerValue, Date upperValue) {
		return NumericDocValuesField.newSlowRangeQuery(field, lowerValue.getTime(), upperValue.getTime());
	}
	
	
	public static BuilderQuery createBoolQuery() {
		return new BuilderQuery();
	}

}

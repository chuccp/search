package com.kanke.search.query.function;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.LongDocValues;

public class DateFuncValueSource extends ValueSource {

	private String field;

	private DateDocValues dateDocValues;
	
	
	
	public DateFuncValueSource(String field,DateDocValues dateDocValues,long value) {
		super();
		this.field = field;
		this.dateDocValues = dateDocValues;
	}
	

	@Override
	public FunctionValues getValues(@SuppressWarnings("rawtypes") Map context, LeafReaderContext readerContext) throws IOException {
		NumericDocValues numericDocValues = DocValues.getNumeric(readerContext.reader(), field);
		return new LongDocValues(this) {
			@Override
			public long longVal(int doc) throws IOException {
				long value =  numericDocValues.longValue();
				System.out.println("value   :"+value);
				return DateFuncValueSource.this.dateDocValues.DateFunc(new Date(value));
			}
		};
	}

	@Override
	public boolean equals(Object o) {

		System.out.println(o);
		
		
		
		
		return true;
	}

	@Override
	public int hashCode() {
		int h = getClass().hashCode();
		h += field.hashCode();
		return h;
	}

	@Override
	public String description() {
		return "func(" + field + ')';
	}

	public void setField(String field) {
		this.field = field;
	}

}

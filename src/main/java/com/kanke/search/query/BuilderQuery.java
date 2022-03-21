package com.kanke.search.query;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.Query;

public class BuilderQuery {
	
	private Builder  builder = new BooleanQuery.Builder();
	
	public BuilderQuery must(Query query) {
		builder = builder.add(query, Occur.MUST);
		return this;
	}
	
	public BuilderQuery should(Query query) {
		builder = builder.add(query, Occur.SHOULD);
		return this;
	}
	
	public BuilderQuery mustNo(Query query) {
		builder = builder.add(query, Occur.MUST_NOT);
		return this;
	}

	public Query build() {
		return builder.build();
	}
}

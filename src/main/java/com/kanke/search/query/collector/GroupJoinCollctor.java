package com.kanke.search.query.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.SimpleCollector;

import com.kanke.search.IndexFactory;
import com.kanke.search.query.GroupIndexBuilders.Join;
import com.kanke.search.query.QueryUtils;

public class GroupJoinCollctor extends SimpleCollector {

	private JoinValue joinValue;

	private IndexFactory indexFactory;

	private List<TermValue> joinTermValueList;
	
	private List<TermValue> hasTermValueList = new ArrayList<TermValue>();

	private GroupDocValues groupDocValues;
	
	private TermValueCache termValueCache;

	private GroupReader groupReader;
	private int docBase = 0;
	private List<TermValue> queryTermValueList = new ArrayList<TermValue>();
	public GroupJoinCollctor(JoinValue joinValue, IndexFactory indexFactory, GroupReader groupReader,TermValueCache termValueCache,
			List<TermValue> joinTermValueList) {
		this.joinValue = joinValue;
		this.termValueCache = termValueCache;
		this.groupDocValues = new GroupDocValues(groupReader.getGroupFields(joinValue.getJoin().getIndex()));
		this.indexFactory = indexFactory;
		this.joinTermValueList = joinTermValueList;
		this.groupReader = groupReader;
	}

	@Override
	public ScoreMode scoreMode() {
		return ScoreMode.COMPLETE_NO_SCORES;
	}

	int num  =  0; 
	
	@Override
	public void collect(int doc) throws IOException {
		groupDocValues.advanceExact(doc);
		TermValue termValue = groupDocValues.getTermValue();
		termValue.setDocId(docBase+doc);
		queryTermValueList.add(termValue);
		if(queryTermValueList.size()==1000) {
			this.groupTermValue(queryTermValueList);
			queryTermValueList.clear();
		}
	}
	
	
	private void groupTermValue(List<TermValue> queryTermValueList) throws IOException {
		 Map<String,JoinValue> joinValuesMap = new HashMap<>();
		Join join = joinValue.getJoin();
		Map<BytesRefValue,TermValue> mapValue = new HashMap<>();
		for(TermValue tv:queryTermValueList) {
			String storeName = join.getFiledName();
			mapValue.put(tv.get(storeName), tv);
			
			List<Join> joins = join.nextJoin();
			for(Join njoin:joins) {
				String filename = njoin.getOnfiledName();
				if(!joinValuesMap.containsKey(filename)) {
					joinValuesMap.put(filename,new JoinValue(join));
				}
				joinValuesMap.get(filename).add(tv.get(filename));
			}
		}
		
		for (Map.Entry<String,JoinValue> entry : joinValuesMap.entrySet()) {
			JoinValue val = entry.getValue();
			GroupJoinCollctor groupJoinCollctor = new GroupJoinCollctor(val, indexFactory,this.groupReader,this.termValueCache,queryTermValueList);
			groupJoinCollctor.exec();
		}
		
		for(TermValue termValue:hasTermValueList) {
			BytesRefValue bytesRefValue = termValue.get(join.getOnfiledName());
			if(mapValue.containsKey(bytesRefValue)) {
				TermValue jtermValue = mapValue.get(bytesRefValue);
				termValue.addTermValue(jtermValue);
				termValueCache.put(join.getIndex(), join.getFiledName(), bytesRefValue, jtermValue);
			}
		}
	}
	@Override
	protected void doSetNextReader(LeafReaderContext context) throws IOException {
		this.groupDocValues.clear();
		this.docBase=context.docBase;
		String[] storeNames = this.groupReader.getSortNames(joinValue.getJoin().getIndex());
		for (String storeName : storeNames) {
			this.groupDocValues.addSort(context, storeName);
		}
	}
	
	
	private void joinTermValue() {
		Join join = joinValue.getJoin();
		for(TermValue tv:joinTermValueList) {
			TermValue termValue = termValueCache.get(join.getIndex(), join.getFiledName(),tv.get(join.getOnfiledName()));
			if(termValue!=null) {
				tv.addTermValue(termValue);
			}else {
				hasTermValueList.add(tv);
			}
		}
	}
	
	
	private void after() throws IOException {
		if(!queryTermValueList.isEmpty()) {
			this.groupTermValue(queryTermValueList);
		}
	}

	public void exec() throws IOException {
		this.joinTermValue();
		Query query = this.joinValue.getJoin().getQuery();
		Join join = joinValue.getJoin();
		Set<String> value = new HashSet<String>();
		for (TermValue tv : hasTermValueList) {
			BytesRefValue bytesRefValue = tv.get(join.getOnfiledName());
			value.add(bytesRefValue.toString());
		}
		Query wQuery = QueryUtils.createTermsQuery(join.getFiledName(), value);
		if (this.joinValue.getJoin().getQuery() == null) {
			query = wQuery;
		} else {
			query = QueryUtils.createBoolQuery().must(query).must(wQuery).build();
		}
		IndexReader indexReader = indexFactory.getIndexReader(joinValue.getJoin().getIndex());
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		indexSearcher.search(query, this);
		this.after();
	}

}

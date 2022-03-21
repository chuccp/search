package com.kanke.search.query.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.SimpleCollector;

import com.kanke.search.IndexFactory;
import com.kanke.search.query.Group;
import com.kanke.search.query.GroupBuilder.Report;
import com.kanke.search.query.GroupIndex;
import com.kanke.search.query.GroupIndexBuilders.Join;
import com.kanke.search.query.GroupResponse;
import com.kanke.search.query.Pageable;
import com.kanke.search.query.selector.CountSelector;
import com.kanke.search.query.selector.SumSelector;
import com.kanke.search.query.selector.TermSelector;
import com.kanke.search.type.GroupType;

public class AllGroupCollector extends SimpleCollector {

	private IndexFactory indexFactory;
	
	
	private GroupReader groupReader;

	private GroupDocValues groupDocValues ; 
	
	private int docBase = 0;
	
	private TermSelector termSelector;
	
	private TermValueCache termValueCache = new TermValueCache();
	
	
	
	private List<TermValue> termValueList = new ArrayList<TermValue>();
	
	public AllGroupCollector(IndexFactory indexFactory,GroupIndex groupIndex,Group group) {
		this.groupReader = new GroupReader(groupIndex, group);
		this.indexFactory = indexFactory;
		this.groupDocValues =new GroupDocValues(group.getGroupField(groupIndex.getIndex()));
		this.termSelector = new TermSelector();
		this.initGroup();
	}
	
	
	private void initGroup() {
		List<Report>  list = groupReader.getReports();
		for(Report report:list) {
			if(report.getGroupType()==GroupType.COUNT) {
				termSelector.AddSelector(new CountSelector(report));
			}else if (report.getGroupType()==GroupType.SUM) {
				termSelector.AddSelector(new SumSelector(report));
			}
		}
	}
	
	
	@Override
	public ScoreMode scoreMode() {
		return ScoreMode.COMPLETE_NO_SCORES;
	}

	@Override
	public void collect(int doc) throws IOException {
		groupDocValues.advanceExact(doc);
		TermValue termValue = groupDocValues.getTermValue();
		termValue.setDocId(doc+this.docBase);
		termValueList.add(termValue);
		if(termValueList.size()==1000) {
			this.groupTermValue(termValueList);
			termValueList.clear();
		}
	}
	
	
	
	private void groupTermValue(List<TermValue> termValueList) throws IOException {
		 Map<String,JoinValue> joinValuesMap = new HashMap<>();
		for(TermValue termValue:termValueList) {
			List<Join> joins = this.groupReader.getJoin();
			for(Join join:joins) {
				String filename = join.getOnfiledName();
				if(!joinValuesMap.containsKey(filename)) {
					joinValuesMap.put(filename,new JoinValue(join));
				}
				joinValuesMap.get(filename).add(termValue.get(filename));
			}
		}
		for (Map.Entry<String,JoinValue> entry : joinValuesMap.entrySet()) {
			JoinValue val = entry.getValue();
			GroupJoinCollctor groupJoinCollctor = new GroupJoinCollctor(val, indexFactory,this.groupReader,this.termValueCache,termValueList);
			groupJoinCollctor.exec();
		}
		for(TermValue termValue:termValueList) {
			termSelector.collect(termValue);
		}
	}
	
	@Override
	protected void doSetNextReader(LeafReaderContext context) throws IOException {
		this.groupDocValues.clear();
		this.docBase=context.docBase;
		String[] storeNames =this.groupReader.getMainSortNames();
		for(String storeName:storeNames) {
			this.groupDocValues.addSort(context, storeName);
		}
	}
	
	
	private void after() throws IOException {
		if(!termValueList.isEmpty()) {
			this.groupTermValue(termValueList);
		}
	}

	private void exec() throws IOException {
		IndexReader  indexReader  = this.indexFactory.getIndexReader(this.groupReader.getMainIndex());
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		indexSearcher.search(this.groupReader.getMainQuery(), this);
		this.after();
		
	}
	
	public GroupResponse search(Pageable pageable) throws IOException{
		this.exec();
		GroupResponse groupResponse = new GroupResponse(pageable,this.termSelector);
		groupResponse.exec();
		return groupResponse;
	}
}

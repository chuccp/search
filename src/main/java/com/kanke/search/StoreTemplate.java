package com.kanke.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.kanke.search.annotation.StoreIndex;
import com.kanke.search.entry.StoreFileld;
import com.kanke.search.entry.StoreFileldIndex;
import com.kanke.search.entry.StoreFileldIndexs;
import com.kanke.search.entry.StoreFilelds;
import com.kanke.search.query.Group;
import com.kanke.search.query.GroupBuilder;
import com.kanke.search.query.GroupBuilders;
import com.kanke.search.query.GroupResponse;
import com.kanke.search.query.Pageable;
import com.kanke.search.query.QueryUtils;
import com.kanke.search.query.collector.AllGroupCollector;
import com.kanke.search.query.group.GroupQuery;
import com.kanke.search.util.DocumentUtil;

public class StoreTemplate {
	


	private Path path;
	private Map<String, IndexWriter> indexWriterMap = new LinkedHashMap<>();

	private Map<String, IndexReader> indexReaderMap = new LinkedHashMap<>();

	public StoreTemplate(Path path) {
		this.path = path;
	}
	
	
	private Map<String, StoreFileldIndex> storeFileldIndexMap = new LinkedHashMap<>();
	

	private void saveStoreFilelds(StoreFilelds storeFilelds) {

		List<StoreFileld> list = storeFilelds.getFields();
		List<StoreFileldIndex> slist = new ArrayList<>();
		for (StoreFileld sf : list) {
			StoreFileldIndex sfi = new StoreFileldIndex();
			sfi.setClassName(storeFilelds.getStoreClazz().getName());
			sfi.setIndexName(storeFilelds.getStoreIndex());
			sfi.setFiledName(sf.getFieldName());
			sfi.setStoreName(sf.getStoreName());
			sfi.setGenericType(sf.getField().getGenericType().getTypeName());
			sfi.setSort(sf.isSort());
			sfi.setId(sfi.getIndexName() + "_" + sfi.getStoreName());
			sfi.setSortName(sf.getStoreName());
			if( storeFileldIndexMap.containsKey(sfi.getId())) {
				if(!storeFileldIndexMap.get(sfi.getId()).equals(sfi)) {
					storeFileldIndexMap.put(sfi.getId(), sfi);
					slist.add(sfi);
				}
			}else {
				storeFileldIndexMap.put(sfi.getId(), sfi);
				slist.add(sfi);
			}
		}
		if (!slist.isEmpty()) {
			try {
				this.writeOrUpdate0(StoreFilelds.get(StoreFileldIndex.class), slist);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	
	private StoreFileldIndexs getStoreFilelds(String index) {
		StoreFileldIndexs storeFileldIndexs = StoreFileldIndexs.CreateStoreFileldIndexs();
		for( StoreFileldIndex sfi:storeFileldIndexMap.values()) {
			if(StringUtils.equals(index, sfi.getIndexName())) {
				storeFileldIndexs.addStoreFileldIndex(sfi);
			}
		}
		if(storeFileldIndexs.isEmpty()) {
			Query q1 = QueryUtils.createTermQuery("indexName", index);
			List<StoreFileldIndex>  list = this.search("StoreFileldIndex", q1, 1000, StoreFileldIndex.class);
			for( StoreFileldIndex sfi:list) {
				storeFileldIndexs.addStoreFileldIndex(sfi);
			}
		}
		return storeFileldIndexs;
	}
	
	

	private <T> void writeOrUpdate0(StoreFilelds storeFilelds, List<T> list) throws IOException {
		IndexWriter indexWriter = this.getIndexWriter(storeFilelds.getStoreIndex());
		if (!list.isEmpty()) {
			String idName = storeFilelds.getIdField().getStoreName();
			for (T t : list) {
				Document doc = DocumentUtil.toDoc(t, storeFilelds);
				indexWriter.updateDocument(new Term(idName, doc.get(idName)), doc);
			}
			indexWriter.flush();
			indexWriter.commit();
		}
	}

	public <T> void writeOrUpdate(List<T> list) throws IOException {
		if (!list.isEmpty()) {
			T t1 = list.get(0);
			StoreFilelds storeFilelds = StoreFilelds.get(t1.getClass());
			writeOrUpdate0(storeFilelds, list);
			this.saveStoreFilelds(storeFilelds);
		}
	}

	public <T> void writeOrUpdate(T t) throws IOException {
		StoreFilelds storeFilelds = StoreFilelds.get(t.getClass());
		this.saveStoreFilelds(storeFilelds);
		String idName = storeFilelds.getIdField().getStoreName();
		IndexWriter indexWriter = this.getIndexWriter(storeFilelds.getStoreIndex());
		Document doc = DocumentUtil.toDoc(t, storeFilelds);
		indexWriter.updateDocument(new Term(idName, doc.get(idName)), doc);
		indexWriter.flush();
		indexWriter.commit();
	}

	public void deleteIndex(String index) throws IOException {
		File filePath = new File(path.toFile(), index);
		FileUtils.deleteDirectory(filePath);

	}

	public GroupResponse group(String index, Group group, Query query, Pageable pageable) {
		IndexReader indexReader = this.getIndexReader(index);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		GroupBuilder  groupBuilder  = GroupBuilders.groupBy(group.getStoreNames()).fieldName(group.getAliasName());
		if(group.isOrder()) {
			if(group.isReverse()) {
				groupBuilder.desc();
			}else {
				groupBuilder.asc();
			}
		}
		AllGroupCollector  allGroupCollector  =  new AllGroupCollector(groupBuilder,this.getStoreFilelds(index));
		try {
			indexSearcher.search(query,allGroupCollector);
			GroupResponse groupResponse = new GroupResponse(allGroupCollector,indexSearcher, pageable);
			groupResponse.exec();
			return groupResponse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public GroupResponse group(String index, GroupBuilder groupBuilder, Query query, Pageable pageable) {
		return this.group(index, groupBuilder, null, query, pageable);
	}
	public GroupResponse group(String index, GroupBuilder groupBuilder, GroupQuery groupQuery,Query query, Pageable pageable) {
		IndexReader indexReader = this.getIndexReader(index);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		AllGroupCollector  allGroupCollector  = new AllGroupCollector(groupBuilder,this.getStoreFilelds(index));
		try {
			indexSearcher.search(query,allGroupCollector);
			GroupResponse groupResponse = new GroupResponse(allGroupCollector,indexSearcher,pageable);
			groupResponse.setGroupQuery(groupQuery);
			groupResponse.exec();
			return groupResponse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	public <T> void write(List<T> list) throws IOException {
		if (list != null && !list.isEmpty()) {
			T t = list.get(0);
			StoreIndex storeIndex = t.getClass().getAnnotation(StoreIndex.class);
			if (storeIndex != null) {
				this.writeOrUpdate(t);
			}
		}
	}

	public void delete(String index, Query query) throws IOException {
		IndexWriter indexWriter = this.getIndexWriter(index);
		indexWriter.deleteDocuments(query);
		indexWriter.flush();
		indexWriter.commit();
	}

	public <T> List<T> search(String index, Query query, com.kanke.search.query.Sort sort, int top, Class<T> clazz) {
		StoreFilelds storeFilelds = StoreFilelds.get(clazz);
		StoreFileld storeFileld = storeFilelds.getStoreFileld(sort.getFileName());
		return this.search0(index, query,
				new Sort(new SortField(storeFileld.getStoreName(), storeFileld.getSortType(), sort.isReverse())), top,
				storeFilelds);

	}
	public <T> List<T> search(String index, Query query,Collector collector ,int top) throws IOException{
		IndexReader indexReader = this.getIndexReader(index);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		indexSearcher.search(query,collector);
		
		
		
		return null;
		
	}

	private <T> List<T> search0(String index, Query query, Sort sort, int top, StoreFilelds storeFilelds) {
		IndexReader indexReader = this.getIndexReader(index);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		try {
			TopDocs topDocs = null;
			if (sort != null) {
				topDocs = indexSearcher.search(query, top, sort);
			} else {
				topDocs = indexSearcher.search(query, top);
			}
			List<T> list = new ArrayList<>(topDocs.scoreDocs.length);
			for (ScoreDoc sd : topDocs.scoreDocs) {
				Document doc = indexSearcher.doc(sd.doc);
				@SuppressWarnings("unchecked")
				T t = (T) DocumentUtil.toEntry(doc, storeFilelds);
				list.add(t);
			}
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> search(String index, Query query, int top, Class<T> clazz) {
		StoreFilelds storeFilelds = StoreFilelds.get(clazz);
		return this.search0(index, query, null, top, storeFilelds);
	}

	public Integer num(String index) {
		IndexReader indexReader = this.getIndexReader(index);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		int num = indexSearcher.getIndexReader().numDocs();
		return num;
	}

	private IndexWriter getIndexWriter(String index) throws IOException {
		if (indexWriterMap.containsKey(index)) {
			return indexWriterMap.get(index);
		} else {
			File filePath = new File(path.toFile(), index);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			FSDirectory fSDirectory = FSDirectory.open(filePath.toPath());
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
			IndexWriter indexWriter = new IndexWriter(fSDirectory, indexWriterConfig);

			indexWriterMap.put(index, indexWriter);
			return indexWriter;
		}
	}

	private IndexReader getIndexReader(String index) {
		if (indexReaderMap.containsKey(index)) {
			return indexReaderMap.get(index);
		} else {
			File filePath = new File(path.toFile(), index);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			try {
				FSDirectory fSDirectory = FSDirectory.open(filePath.toPath());
				IndexReader reader = DirectoryReader.open(fSDirectory);
				indexReaderMap.put(index, reader);
				return reader;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}

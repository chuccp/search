package com.kanke.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.kanke.search.annotation.StoreIndex;
import com.kanke.search.model.LuceneAbstract;
import com.kanke.search.util.DocumentUtil;

public class StoreTemplate {

	private Path path;
	private Map<String, IndexWriter> indexWriterMap = new LinkedHashMap<>();

	private Map<String, IndexReader> indexReaderMap = new LinkedHashMap<>();

	public StoreTemplate(Path path) {
		this.path = path;
	}

	public <T extends LuceneAbstract> void write(String index, List<T> list) throws IOException {
		IndexWriter indexWriter = this.getIndexWriter(index);
		for (T t : list) {
			indexWriter.addDocument(DocumentUtil.toDoc(t));
		}
		indexWriter.flush();
		indexWriter.commit();
	}

	public <T extends LuceneAbstract> void write(T t) throws IOException {
		StoreIndex storeIndex = t.getClass().getAnnotation(StoreIndex.class);
		if (storeIndex != null) {
			IndexWriter indexWriter = this.getIndexWriter(storeIndex.value());
			indexWriter.addDocument(DocumentUtil.toDoc(t));
			indexWriter.flush();
			indexWriter.commit();
		}
	}

	public <T extends LuceneAbstract> void write(List<T> list) throws IOException {
		if (list != null && !list.isEmpty()) {
			T t = list.get(0);
			StoreIndex storeIndex = t.getClass().getAnnotation(StoreIndex.class);
			if (storeIndex != null) {
				this.write(storeIndex.value(), list);
			}
		}
	}

	public <T extends LuceneAbstract> List<T> search(String index, Query query, int top, Class<T> clazz) {
		IndexReader indexReader = this.getIndexReader(index);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		try {
			TopDocs topDocs = indexSearcher.search(query, top);
			List<T> list = new ArrayList<>(topDocs.scoreDocs.length);
			for (ScoreDoc sd : topDocs.scoreDocs) {
				Document doc = indexSearcher.doc(sd.doc);
				T t = DocumentUtil.toEntry(doc, clazz);
				list.add(t);
			}
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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

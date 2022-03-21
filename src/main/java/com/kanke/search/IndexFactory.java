package com.kanke.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class IndexFactory {
	
	private static Map<String, IndexWriter> indexWriterMap = new LinkedHashMap<>();

	private static Map<String, IndexReader> indexReaderMap = new LinkedHashMap<>();
	
	private Path path;
	
	public IndexFactory(Path path) {
		this.path = path;
	}
	
	public  IndexReader getIndexReader(String index) {
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
	
	public IndexWriter getIndexWriter(String index) throws IOException {
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
	
	public void deleteIndex(String index) throws IOException {
		File filePath = new File(path.toFile(), index);
		FileUtils.deleteDirectory(filePath);

	}
	

}

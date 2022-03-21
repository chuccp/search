package com.kanke.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.lucene.search.Query;
import org.junit.jupiter.api.Test;

import com.kanke.search.model.Log;
import com.kanke.search.model.User;
import com.kanke.search.query.Bucket;
import com.kanke.search.query.Group;
import com.kanke.search.query.GroupBuilders;
import com.kanke.search.query.GroupField;
import com.kanke.search.query.GroupIndex;
import com.kanke.search.query.GroupIndexBuilders;
import com.kanke.search.query.GroupResponse;
import com.kanke.search.query.Pageable;
import com.kanke.search.query.QueryUtils;

public class TestQuery {


	StoreTemplate getStoreTemplate(){
		return new StoreTemplate(new File("C:\\Users\\cooge\\Documents\\search").toPath());
		
	}
	
	
	@Test
	public void should_test_every_test() {
		

	}
	
	@Test
	public void should_test_every_test2() {
		StoreTemplate storeTemplate = getStoreTemplate();
		Query query1 =QueryUtils.MatchAllDocsQuery();
		GroupIndex groupIndex = GroupIndexBuilders.index("Log",query1).Join("User", "userId",query1).on("userId").build();
		Group group = GroupBuilders.groupBy(GroupField.createGroupField("Log","hour"),GroupField.createGroupField("User", "userName")).count("num").desc().build();
		GroupResponse groupResponse = storeTemplate.group(groupIndex,group,Pageable.page(10));
		
		List<Bucket> bucketList = groupResponse.getBuckets();
		
		for(Bucket bucket:bucketList) {
			System.out.println(bucket.getTermValue().getValue("hour")+"    "+bucket.getTermValue().getValue("userName")+"  "+bucket.getFieldValue("num").getValue());
		}
	}
	
	
	@Test
	public void gen_test_every_test() throws IOException {
		
		List<User> ulist =new ArrayList<>();
		List<Log> llist =new ArrayList<>();
		
		StoreTemplate storeTemplate = getStoreTemplate();
		int logId = 0;
		for(int i = 0;i<1000;i++) {
			User u = new User();
			u.setUserId(String.valueOf(i));
			u.setUserName("name_"+i);
			ulist.add(u);
			for(int j = 0;j<1000;j++) {
				logId++;
				Log log = new Log();
				log.setUserId(u.getUserId());
				log.setLogId(String.valueOf(logId));
				log.setTime(new Date());
				log.setHour(RandomUtils.nextInt(0, 12));
				log.setVideoId(String.valueOf(j));
				log.setWatch(log.getTime().getTime());
				llist.add(log);
			}
			
		}
		
		storeTemplate.writeOrUpdate(ulist);
		storeTemplate.writeOrUpdate(llist);
		
	}
	
	
}

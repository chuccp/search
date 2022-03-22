package com.kanke.search.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kanke.search.query.group.GroupTermQuery;
import com.kanke.search.type.GroupType;

public class GroupBuilder {

	private List<Report> reports;

	private GroupField[] groupFields;
	
	private GroupTermQuery groupQuery;
	
	

	public GroupTermQuery getGroupQuery() {
		return groupQuery;
	}

	public void setGroupQuery(GroupTermQuery groupQuery) {
		this.groupQuery = groupQuery;
	}

	private GroupBuilder root;

	public GroupBuilder(GroupField... groupFields) {
		this.groupFields = groupFields;
		this.reports = new ArrayList<>();
		this.root = this;
	}

	public GroupBuilder(GroupBuilder root) {
		this.root = root;
	
	}

	private GroupType groupType;

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	public Group build() {
		Group group = new Group();
		group.setGroupFields(this.root.groupFields);
		for(Report report:this.root.reports) {
			group.addReport(report);
		}
		return group;
	}

	public GroupBuilder count(String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report = new Report(null, aliasName);
		groupBuilder.report.setGroupType(GroupType.COUNT);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	public GroupBuilder sum(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report = new Report(groupField, aliasName);
		groupBuilder.report.setGroupType(GroupType.SUM);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	public GroupBuilder max(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report = new Report(groupField, aliasName);
		groupBuilder.report.setGroupType(GroupType.MAX);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	public GroupBuilder min(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report = new Report(groupField, aliasName);
		groupBuilder.report.setGroupType(GroupType.MIN);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	public GroupBuilder avg(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report = new Report(groupField, aliasName);
		groupBuilder.report.setGroupType(GroupType.AVG);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	private Report report;

	public GroupBuilder desc() {
		if (report != null)
			report.desc();
		return this;
	}

	public GroupBuilder asc() {
		if (report != null)
			report.asc();
		return this;
	}

	public class Report {

		public Report(GroupField groupField, String aliasName) {
			this.groupField = groupField;
			this.aliasName = aliasName;
		}

		private boolean reverse = false;

		private boolean isOrder = false;

		private GroupType groupType;

		private GroupField groupField;

		private String aliasName;

		public GroupType getGroupType() {
			return groupType;
		}

		public void setGroupType(GroupType groupType) {
			this.groupType = groupType;
		}

		public GroupField getGroupField() {
			return groupField;
		}

		public void setGroupField(GroupField groupField) {
			this.groupField = groupField;
		}

		public String getAliasName() {
			return aliasName;
		}

		public void setAliasName(String aliasName) {
			this.aliasName = aliasName;
		}

		public Report desc() {
			reverse = true;
			this.isOrder = true;
			return this;
		}

		public Report asc() {
			reverse = false;
			this.isOrder = true;
			return this;
		}

		public boolean isReverse() {
			return reverse;
		}

		public boolean isOrder() {
			return isOrder;
		}
	}

	private String aliasName;
	
	public GroupBuilder fieldName(String aliasName) {
		if(this.reports.isEmpty()) {
			this.aliasName = aliasName;
			return count(aliasName);
		}else {
			this.aliasName = aliasName;
			return this;
		}
	}

	public GroupBuilder addGroupBuilder(GroupBuilder groupBuilder) {
		List<Report>  reports = groupBuilder.reports;
		Report report = reports.get(0);
		report.aliasName = groupBuilder.aliasName;
		this.root.reports.add(report);
		return this;
	}

}

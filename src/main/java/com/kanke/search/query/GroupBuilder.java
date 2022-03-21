package com.kanke.search.query;

import java.util.ArrayList;
import java.util.List;

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
		this.root.setGroupType(GroupType.COUNT);
		this.root.report = new Report(null, aliasName);
		return this.root;
	}

	public GroupBuilder sum(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report.setGroupType(GroupType.SUM);
		groupBuilder.report = new Report(groupField, aliasName);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	public GroupBuilder max(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report.setGroupType(GroupType.MAX);
		groupBuilder.report = new Report(groupField, aliasName);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	public GroupBuilder min(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report.setGroupType(GroupType.MIN);
		groupBuilder.report = new Report(groupField, aliasName);
		this.root.reports.add(groupBuilder.report);
		return groupBuilder;
	}

	public GroupBuilder avg(GroupField groupField, String aliasName) {
		GroupBuilder groupBuilder = new GroupBuilder(this.root);
		groupBuilder.report.setGroupType(GroupType.AVG);
		groupBuilder.report = new Report(groupField, aliasName);
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

}

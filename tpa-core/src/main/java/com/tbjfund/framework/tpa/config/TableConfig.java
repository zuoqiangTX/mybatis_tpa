package com.tbjfund.framework.tpa.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/4/16.
 */
public class TableConfig {

	// 模型类型：简单模型只包含xml,dao,service,model；复杂模型包含分层中的DTO，convert等信息
	// @see ModelType.java
	private String modelType;

	// com.tbjfund.framework
	private String packageName;

	// UserManager
	private String beanName;

	// userManager
	private String injectName;

	// user_manager
	private String tableName;

	// com.tbjfund.framework.UserManager
	private String namespace;

	// @Table(aliasName='')
	private String typeAlisName;

	private ColumnConfig primaryKey;

	private List<ColumnConfig> uniques = new ArrayList<ColumnConfig>();
	
	private List<ColumnConfig> includes = new ArrayList<ColumnConfig>();

	private List<ColumnConfig> columns;

	// comment
	private String comment;

	private String alterMsg;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<ColumnConfig> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnConfig> columns) {
		this.columns = columns;
	}

	public String getTypeAlisName() {
		return typeAlisName;
	}

	public void setTypeAlisName(String typeAlisName) {
		this.typeAlisName = typeAlisName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ColumnConfig getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ColumnConfig primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getInjectName() {
		return injectName;
	}

	public void setInjectName(String injectName) {
		this.injectName = injectName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getAlterMsg() {
		return alterMsg;
	}

	public void setAlterMsg(String alterMsg) {
		this.alterMsg = alterMsg;
	}

	public List<ColumnConfig> getUniques() {
		return uniques;
	}

	public void setUniques(List<ColumnConfig> uniques) {
		this.uniques = uniques;
	}

	public List<ColumnConfig> getIncludes() {
		return includes;
	}

	public void setIncludes(List<ColumnConfig> includes) {
		this.includes = includes;
	}
}

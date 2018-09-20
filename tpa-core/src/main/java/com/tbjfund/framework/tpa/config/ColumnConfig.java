package com.tbjfund.framework.tpa.config;

/**
 * Created by sidawei on 16/4/16.
 */
public class ColumnConfig {

	private boolean isPrimaryKey;

	private String findByIdMapperKeyType;

	private String findByIdJavaKeyType;

	private String columnName;
	
	private String upperColumnName;
	
	private String fUpperColumnName;

	private String columnType;

	private String fieldName;

	private String javaType;

	private String SimpleJavaType;

	private String comment;

	private String whereParam;

	public ColumnConfig(boolean isPrimaryKey, String columnName, String fieldName) {
		this.isPrimaryKey = isPrimaryKey;
		this.columnName = columnName;
		this.fieldName = fieldName;
	}

	public ColumnConfig(String columnName, String fieldName) {
		this.columnName = columnName;
		this.fieldName = fieldName;
	}

	public ColumnConfig() {

	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getSimpleJavaType() {
		return SimpleJavaType;
	}

	public void setSimpleJavaType(String simpleJavaType) {
		SimpleJavaType = simpleJavaType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setFindByIdKeyInfo(String columnType) {
		if ("BIGINT".equals(columnType) || "INTEGER".equals(columnType)) {
			this.findByIdMapperKeyType = "java.lang.Long";
			this.findByIdJavaKeyType = "Long";
		} else {
			this.findByIdMapperKeyType = "string";
			this.findByIdJavaKeyType = "String";
		}
	}

	public String getWhereParam() {
		return whereParam;
	}

	public void setWhereParam(String whereParam) {
		this.whereParam = whereParam;
	}

	public String getFindByIdMapperKeyType() {
		return findByIdMapperKeyType;
	}

	public String getFindByIdJavaKeyType() {
		return findByIdJavaKeyType;
	}

	public void setFindByIdMapperKeyType(String findByIdMapperKeyType) {
		this.findByIdMapperKeyType = findByIdMapperKeyType;
	}

	public void setFindByIdJavaKeyType(String findByIdJavaKeyType) {
		this.findByIdJavaKeyType = findByIdJavaKeyType;
	}

	public String getUpperColumnName() {
		return upperColumnName;
	}

	public void setUpperColumnName(String upperColumnName) {
		this.upperColumnName = upperColumnName;
	}

	public String getfUpperColumnName() {
		return fUpperColumnName;
	}

	public void setfUpperColumnName(String fUpperColumnName) {
		this.fUpperColumnName = fUpperColumnName;
	}

}

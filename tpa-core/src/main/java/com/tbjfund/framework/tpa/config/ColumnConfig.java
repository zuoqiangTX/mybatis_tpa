package com.tbjfund.framework.tpa.config;

/**
 * Created by sidawei on 16/4/16.
 */
public class ColumnConfig {

    private boolean isPrimaryKey;

    private String columnName;

    private String fieldName;

    private String javaType;

    private String SimpleJavaType;

    public ColumnConfig(boolean isPrimaryKey, String columnName, String fieldName) {
        this.isPrimaryKey = isPrimaryKey;
        this.columnName = columnName;
        this.fieldName = fieldName;
    }

    public ColumnConfig(String columnName, String fieldName) {
        this.columnName = columnName;
        this.fieldName = fieldName;
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
}

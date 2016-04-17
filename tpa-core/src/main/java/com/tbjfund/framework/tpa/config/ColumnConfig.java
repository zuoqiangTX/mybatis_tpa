package com.tbjfund.framework.tpa.config;

/**
 * Created by sidawei on 16/4/16.
 */
public class ColumnConfig {

    private boolean isPrimaryKey;

    private String columnName;

    private String fieldName;

    private String fieldType;

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

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}

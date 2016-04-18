package com.tbjfund.framework.tpa.config;

import java.util.List;

/**
 * Created by sidawei on 16/4/16.
 */
public class TableConfig {

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

    private List<ColumnConfig> columns;

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
}

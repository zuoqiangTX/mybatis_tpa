package com.tbjfund.framework.tpa.webapp.model;

/**
 * Created by sidawei on 16/4/26.
 */
public class Database {

    private String dbType = "mysql";

    private String ip;

    private String port;

    private String charset = "utf-8";

    private String userName;

    private String password;

    private String schema;

    public Database(String ip, String port, String schema, String userName, String password) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.schema = schema;
    }

    public Database(String ip, String port, String schema) {
        this.ip = ip;
        this.port = port;
        this.schema = schema;
    }

    public Database() {

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Database){
            return ip.equals(((Database) o).getIp()) && port.equals(((Database) o).getPort()) && schema.equals(((Database) o).getSchema());
        }
        return false;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}

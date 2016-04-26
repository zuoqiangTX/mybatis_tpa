package com.tbjfund.framework.tpa.webapp.controller;

import com.alibaba.fastjson.JSON;
import com.tbjfund.framework.tpa.config.ColumnConfig;
import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.utils.StringUtils;
import com.tbjfund.framework.tpa.webapp.HttpController;
import com.tbjfund.framework.tpa.webapp.service.JdbcTypeService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sidawei on 16/4/25.
 */
public class Columns_List implements HttpController {

    @Override
    public String service(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //resp.setCharacterEncoding("UTF-8");
        final String ip = req.getParameter("ip");
        final String port = req.getParameter("port");
        final String userName = req.getParameter("userName");
        final String password = req.getParameter("password");
        final String schema = req.getParameter("schema");
        final String table = req.getParameter("table");

        req.setAttribute("ip", ip);
        req.setAttribute("port", port);
        req.setAttribute("userName", userName);
        req.setAttribute("password", password);
        req.setAttribute("schema", schema);

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + schema + "?useUnicode=true&characterEncoding=utf-8"
                    , userName, password);
            TableConfig tableConfig = getTable(connection, table);
            connection.close();
            req.setAttribute("table", tableConfig);
            BASE64Encoder encoder = new BASE64Encoder();
            req.setAttribute("tableConfigJson", encoder.encode(JSON.toJSONString(tableConfig).getBytes("utf-8")));
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return "columns.jsp";
    }

    private TableConfig getTable(Connection conn, String table) throws SQLException, UnsupportedEncodingException {
        TableConfig tableConfig = new TableConfig();
        DatabaseMetaData dbMetData = conn.getMetaData();
        List<ColumnConfig> columns = getColumns(dbMetData, table);
        String beanTypeName = StringUtils.getJavaName(table);
        tableConfig.setPackageName("model." + beanTypeName);
        tableConfig.setBeanName(beanTypeName);
        tableConfig.setInjectName(StringUtils.getFistLowName(beanTypeName));
        tableConfig.setTableName(table);
        tableConfig.setNamespace("model." + beanTypeName);
        tableConfig.setColumns(columns);
        if (columns != null){
            for (ColumnConfig columnConfig : columns){
                if (columnConfig.isPrimaryKey()){
                    tableConfig.setPrimaryKey(columnConfig);
                    break;
                }
            }
        }
        return tableConfig;
    }

    private List<ColumnConfig> getColumns(DatabaseMetaData metaData, String table) throws SQLException, UnsupportedEncodingException {
        List<ColumnConfig> columns = new LinkedList<ColumnConfig>();
        // 根据表名提前表里面信息：
        ResultSet primaryKeySet = metaData.getPrimaryKeys(null, null, table);
        String primaryKey = null;
        while (primaryKeySet.next()){
            primaryKey = primaryKeySet.getString("COLUMN_NAME");
        }
        ResultSet colRet = metaData.getColumns(null, "%", table, "%");
        while (colRet.next()) {
            String columnName = colRet.getString("COLUMN_NAME");
            Integer columnType = colRet.getInt("DATA_TYPE");
            String typeName = colRet.getString("TYPE_NAME");
            String comment = colRet.getString("REMARKS");
            comment = new String(comment.getBytes(), "utf-8");
            System.out.println(comment);
            boolean isPrimaryKey = false;
            if (columnName.equals(primaryKey)){
                isPrimaryKey = true;
            }
            try{
                ColumnConfig columnConfig = buildConlumnConfig(columnName, columnType, isPrimaryKey);
                columnConfig.setComment(comment);
                columns.add(columnConfig);
            }catch (Exception e){
                throw new RuntimeException("ConlumnConfig构建失败:"+table + " " + columnName + " " + columnType + "" + typeName);
            }
        }
        return columns;
    }

    private ColumnConfig buildConlumnConfig(String columnName, Integer columnType, boolean isPrimaryKey){
        ColumnConfig column = new ColumnConfig(isPrimaryKey, columnName, StringUtils.getFistLowName(StringUtils.getJavaName(columnName)));
        JdbcTypeService.JdbcType type = JdbcTypeService.getInstances().getJavaType(columnType);
        column.setColumnType(type.getJdbcType());
        column.setJavaType(type.getJavaType());
        column.setSimpleJavaType(StringUtils.getSimpleName(type.getJavaType()));
        return column;
    }
}

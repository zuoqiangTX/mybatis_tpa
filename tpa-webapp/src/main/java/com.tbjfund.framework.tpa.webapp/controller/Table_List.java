package com.tbjfund.framework.tpa.webapp.controller;

import com.tbjfund.framework.tpa.webapp.HttpController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/4/25.
 */
public class Table_List implements HttpController{

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String service(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String ip = req.getParameter("ip");
        final String port = req.getParameter("port");
        final String userName = req.getParameter("userName");
        final String password = req.getParameter("password");
        final String schema = req.getParameter("schema");

        req.setAttribute("ip", ip);
        req.setAttribute("port", port);
        req.setAttribute("userName", userName);
        req.setAttribute("password", password);
        req.setAttribute("schema", schema);

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/"+schema+"?useUnicode=true&characterEncoding=utf-8"
                                                                    ,userName, password);
            List<String> tables = getTables(connection);
            connection.close();
            req.setAttribute("tables", tables);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return "tables.jsp";
    }

    private List<String> getTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<String>();
        DatabaseMetaData dbMetData = conn.getMetaData();
        ResultSet rs = dbMetData.getTables(null, null, null, new String[] { "TABLE", "VIEW" });
        while (rs.next()) {
            if (rs.getString(4) != null && (rs.getString(4).equalsIgnoreCase("TABLE") || rs .getString(4).equalsIgnoreCase("VIEW"))) {
                String tableName = rs.getString(3).toLowerCase();
                tables.add(tableName);
            }
        }
        return tables;
    }
}

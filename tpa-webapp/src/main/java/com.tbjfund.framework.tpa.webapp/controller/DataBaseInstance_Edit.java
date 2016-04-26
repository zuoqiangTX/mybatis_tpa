package com.tbjfund.framework.tpa.webapp.controller;

import com.tbjfund.framework.tpa.webapp.HttpController;
import com.tbjfund.framework.tpa.webapp.model.Database;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sidawei on 16/4/25.
 */
public class DataBaseInstance_Edit implements HttpController{

    @Override
    public String service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getParameter("ip");
        String port = req.getParameter("port");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String schema = req.getParameter("schema");

        if (StringUtils.isBlank(port)){
            port = "3306";
        }

        if (StringUtils.isBlank(ip)){
            ip = "192.168.1.105";
        }

        Database temp = new Database(ip, port, schema, userName, password);

        req.setAttribute("database", temp);
        return "database_instance_edit.jsp";
    }
}

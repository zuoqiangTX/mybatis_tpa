package com.tbjfund.framework.tpa.webapp.controller;

import com.tbjfund.framework.tpa.webapp.HttpController;
import com.tbjfund.framework.tpa.webapp.ZkHolder;
import com.tbjfund.framework.tpa.webapp.ZkTemplate;
import com.tbjfund.framework.tpa.webapp.model.Database;
import com.tbjfund.framework.tpa.webapp.service.DataBaseInstanceService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/4/25.
 */
public class DataBaseInstance_Edit_Submit implements HttpController{

    @Override
    public String service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String original_ip = req.getParameter("original_ip");
        final String original_port = req.getParameter("original_port");
        final String original_schema = req.getParameter("original_schema");

        final String ip = req.getParameter("ip");
        final String port = req.getParameter("port");
        final String userName = req.getParameter("userName");
        final String password = req.getParameter("password");
        final String schema = req.getParameter("schema");
        if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(schema)){
            //delete
            if (StringUtils.isNotBlank(original_ip) && StringUtils.isNotBlank(original_port) && StringUtils.isNotBlank(original_schema)){
                DataBaseInstanceService.delete(new Database(original_ip, original_port, original_schema));
            }
            //add
            final Database temp = new Database(ip, port, schema, userName, password);
            DataBaseInstanceService.add(temp);
        }
        resp.sendRedirect("/");
        return null;
    }

}

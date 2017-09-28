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
import java.util.Iterator;
import java.util.List;

/**
 * Created by sidawei on 16/4/25.
 */
public class DataBaseInstance_Edit_Delete implements HttpController{

    @Override
    public String service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String ip = req.getParameter("ip");
        final String port = req.getParameter("port");
        final String schema = req.getParameter("schema");
        if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port) && StringUtils.isNotBlank(schema)){
            //add
            final Database temp = new Database(ip, port, schema);
            DataBaseInstanceService.delete(temp);
        }
        resp.sendRedirect("/");
        return null;
    }
}

package com.tbjfund.framework.tpa.webapp.controller;

import com.tbjfund.framework.tpa.webapp.HttpController;
import com.tbjfund.framework.tpa.webapp.ZkHolder;
import com.tbjfund.framework.tpa.webapp.ZkTemplate;
import com.tbjfund.framework.tpa.webapp.model.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by sidawei on 16/4/25.
 */
public class DataBaseInstance_List implements HttpController{

    @Override
    public String service(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ZkTemplate.doInZookeeper(new ZkTemplate.Template() {
            @Override
            public void run(ZkHolder holder) {
                List<Database> databaseList = holder.getArray("/tpa/databases", Database.class);
                req.setAttribute("database_Instance", databaseList);
            }
        });

        return "database_instance.jsp";
    }
}

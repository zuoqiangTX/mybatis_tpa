package com.tbjfund.framework.tpa.webapp;

import com.tbjfund.framework.tpa.webapp.controller.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sidawei on 16/4/26.
 * 
 */
public class DispatcherServlet implements Filter {

    private final Map<String, HttpController> controllers;

    {
        controllers = new ConcurrentHashMap<String, HttpController>();
        controllers.put("/",                    new DataBaseInstance_List());

        controllers.put("/instance_edit",       new DataBaseInstance_Edit());
        controllers.put("/instance_edit_submit",new DataBaseInstance_Edit_Submit());
        controllers.put("/instance_edit_delete",new DataBaseInstance_Edit_Delete());

        controllers.put("/tables",new Table_List());
        controllers.put("/columns",new Columns_List());

        controllers.put("/down",new Download());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        HttpController controller = controllers.get(uri);
        if (controller == null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String jsp = controller.service(req, resp);
        if (jsp != null){
            req.getRequestDispatcher(jsp).forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}

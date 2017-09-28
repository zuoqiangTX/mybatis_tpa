package com.tbjfund.framework.tpa.webapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sidawei on 16/4/26.
 */
public interface HttpController {

    String service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}

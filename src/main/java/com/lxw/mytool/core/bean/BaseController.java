package com.lxw.mytool.core.bean;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    public Map<String, String> getParam(HttpServletRequest request) {
        Map<String, String> qm = new HashMap<String, String>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            String paramValue = request.getParameter(parameterName);
            qm.put(parameterName, paramValue);
        }
        return qm;
    }
}

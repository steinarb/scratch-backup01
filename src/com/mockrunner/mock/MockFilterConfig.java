package com.mockrunner.mock;

import java.util.Enumeration;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 * Mock implementation of <code>FilterConfig</code>.
 * @deprecated use {@link com.mockrunner.mock.web.MockFilterConfig}
 * this class will be removed in the next release
 */
public class MockFilterConfig implements FilterConfig
{
    private ServletContext context;
    
    public void setupServletContext(ServletContext context)
    {
        this.context = context;
    }
    
    public String getInitParameter(String key)
    {
        return getServletContext().getInitParameter(key);
    }

    public Enumeration getInitParameterNames()
    {
        return getServletContext().getInitParameterNames();
    }
    
    public String getFilterName()
    {
        return "";
    }

    public ServletContext getServletContext()
    {
        return context;
    }
}

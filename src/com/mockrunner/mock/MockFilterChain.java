package com.mockrunner.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Mock implementation of <code>FilterChain</code>.
 * @deprecated use {@link com.mockrunner.mock.web.MockFilterChain}
 * this class will be removed in the next release
 */
public class MockFilterChain implements FilterChain
{
	private ArrayList filters = new ArrayList();
	private Servlet servlet;
	private Iterator iterator;
    private ServletRequest lastRequest;
    private ServletResponse lastResponse;
	
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException
	{
        lastRequest = request;
        lastResponse = response;
        if(null == iterator)
		{
			iterator = filters.iterator();
		}
		if(iterator.hasNext())
		{
			Filter nextFilter = (Filter)iterator.next();
			nextFilter.doFilter(request, response, this);
		}
		else
		{
			iterator = null;
            if(null == servlet) return;
			servlet.service(request, response);
		}
	}

	public void addFilter(Filter filter) 
	{
		filters.add(filter);
	}
	
	public void addFilter(Class filterClass) 
	{
		if(!Filter.class.isAssignableFrom(filterClass))
		{
			throw new RuntimeException("filterClass must be an instance of javax.servlet.Filter");
		}
		try
		{
			filters.add(filterClass.newInstance());
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
			throw new RuntimeException(exc.getMessage());
		}
	}
	
	public void setServlet(Servlet servlet) 
	{
		this.servlet = servlet;
	}

	public void release()
	{
		filters.clear();
		setServlet(null);
	}
    
    public ServletRequest getLastRequest()
    {
        return lastRequest;
    }

    public ServletResponse getLastResponse()
    {
        return lastResponse;
    }
}

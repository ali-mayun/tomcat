package com.ty.process.impl;


import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;
import com.ty.process.Processor;
import com.ty.servlet.Servlet;
import com.ty.servlet.impl.ErrorServlet;

public class DynamicServletProcessor implements Processor {
	/** 所有相关处理的servlet都放在这个包下
     */
    private static final String PACKAGE_NAME = "com.ty.servlet.impl.";
    
    @Override
    public void process(Request request, Response response) {
        String servletName = request.getServletName();
        Class<?> clazz = null;
        Servlet servlet = null;
        try {
            clazz = Class.forName(PACKAGE_NAME + servletName);
            servlet = (Servlet) clazz.newInstance();
        } catch (Exception e) {            
            servlet = new ErrorServlet();
        } 
        servlet.process(request, response);
    }
}

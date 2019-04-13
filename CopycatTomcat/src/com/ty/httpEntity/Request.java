package com.ty.httpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** 
   * 此类主要是对请求的封装
 */
public class Request {
	private InputStream input;
    
    private String url;    

    private String servletName;
    
    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Request(InputStream input) {
        this.input = input;
    }
    
    /** 此方法主要包括两个作用
     *         1、获取客户端请求的相关数据
     *         2、解析出请求url，并且根据具体url去找到对应的processor
     */
    public void resolve() {
        String requestStr = resolveInput(input);
        if(requestStr == null || requestStr.length() == 0) {
            return;
        }
        resolveURL(requestStr);
    }
    
    /** 从客户端获取请求相关数据，数据样式如下：
     * GET /static/tomcatProTest HTTP/1.1
     * Host: localhost:8088
     * Connection: keep-alive
     * Upgrade-Insecure-Requests: 1
     * User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36
     * Accept: text/html,application/xhtml+xml,application/xml;
     * Accept-Encoding: gzip, deflate, sdch, br
     * Accept-Language: zh-CN,zh;
     */
    private String resolveInput(InputStream input) {        
        StringBuilder stringBuilder = new StringBuilder();
        String data = null;
        try {
            /** 关于BufferedReader有个注意项，当读取到的数据为""，会存在阻塞现象，因此这里判断长度是否为0
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));    
            while((data = reader.readLine()) != null && data.length() != 0) {
                stringBuilder.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    
    /** 
     * HTTP请求头第一行一般为GET /dynamic/helloServlet HTTP/1.1
             * 由于实现的只是简单的Tomcat功能，不实现解析页面传参，另外对于请求url定义如下：
     *         1、动态请求 /dynamic/对应的servlet名称
     *        2、静态资源请求 /static/静态资源名称
     * resolveURL方法是用于切割出/dynamic/helloServlet
     */
    private void resolveURL(String requestStr) {
        int firstSpaceIndex = requestStr.indexOf(" ");
        int secondSpaceIndex = requestStr.indexOf(" ", firstSpaceIndex + 1);
        String url = requestStr.substring(firstSpaceIndex + 1, secondSpaceIndex);
        setUrl(url);
    }
}

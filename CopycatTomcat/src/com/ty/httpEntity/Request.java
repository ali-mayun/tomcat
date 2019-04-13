package com.ty.httpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** 
   * ������Ҫ�Ƕ�����ķ�װ
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
    
    /** �˷�����Ҫ������������
     *         1����ȡ�ͻ���������������
     *         2������������url�����Ҹ��ݾ���urlȥ�ҵ���Ӧ��processor
     */
    public void resolve() {
        String requestStr = resolveInput(input);
        if(requestStr == null || requestStr.length() == 0) {
            return;
        }
        resolveURL(requestStr);
    }
    
    /** �ӿͻ��˻�ȡ����������ݣ�������ʽ���£�
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
            /** ����BufferedReader�и�ע�������ȡ��������Ϊ""�����������������������жϳ����Ƿ�Ϊ0
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
     * HTTP����ͷ��һ��һ��ΪGET /dynamic/helloServlet HTTP/1.1
             * ����ʵ�ֵ�ֻ�Ǽ򵥵�Tomcat���ܣ���ʵ�ֽ���ҳ�洫�Σ������������url�������£�
     *         1����̬���� /dynamic/��Ӧ��servlet����
     *        2����̬��Դ���� /static/��̬��Դ����
     * resolveURL�����������и��/dynamic/helloServlet
     */
    private void resolveURL(String requestStr) {
        int firstSpaceIndex = requestStr.indexOf(" ");
        int secondSpaceIndex = requestStr.indexOf(" ", firstSpaceIndex + 1);
        String url = requestStr.substring(firstSpaceIndex + 1, secondSpaceIndex);
        setUrl(url);
    }
}

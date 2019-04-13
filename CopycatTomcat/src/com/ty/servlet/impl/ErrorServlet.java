package com.ty.servlet.impl;

import java.io.OutputStream;

import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;
import com.ty.servlet.Servlet;

public class ErrorServlet implements Servlet {

	@Override
    public void process(Request request, Response response) {
        OutputStream output = response.getOutput();
        String succMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                + "Content-Length: 21\r\n" + "\r\n" + "«Î«Ûurl≥ˆœ÷¥ÌŒÛ";
        try {
            output.write(succMessage.getBytes("utf-8"));
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}

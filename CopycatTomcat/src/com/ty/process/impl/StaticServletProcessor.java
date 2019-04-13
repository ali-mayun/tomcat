package com.ty.process.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;
import com.ty.process.Processor;

public class StaticServletProcessor implements Processor {
	@Override
    public void process(Request request, Response response) {
        //为了省事，默认都是取txt文件
        File file = new File(Processor.prefix, request.getServletName() + ".txt");
        FileInputStream fis = null;
        BufferedReader reader = null;
        String data = null;
        StringBuilder stringBuilder = new StringBuilder();
        OutputStream output = response.getOutput();
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
                /** 由于返回数据要符合http响应头的格式，所以会存在一个空行，因此这里不能判断data.length != 0的条件
                 */
                while((data = reader.readLine()) != null) {
                    stringBuilder.append(data + "\r\n");
                }

                output.write(stringBuilder.toString().getBytes("utf-8"));
                output.flush();
            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";

                output.write(errorMessage.getBytes());
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}

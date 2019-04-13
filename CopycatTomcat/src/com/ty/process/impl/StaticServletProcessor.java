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
        //Ϊ��ʡ�£�Ĭ�϶���ȡtxt�ļ�
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
                /** ���ڷ�������Ҫ����http��Ӧͷ�ĸ�ʽ�����Ի����һ�����У�������ﲻ���ж�data.length != 0������
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

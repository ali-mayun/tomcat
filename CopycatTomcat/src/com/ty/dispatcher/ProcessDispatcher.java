package com.ty.dispatcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;
import com.ty.process.Processor;
import com.ty.process.impl.DynamicServletProcessor;
import com.ty.process.impl.StaticServletProcessor;

/** ������Ϊһ��������ַ��������ݿͻ�������url������(������̬��Դ�Լ���̬��Դ����)��ȥ��ʼ����ͬ��processor
 * �����ڴ�ʱ��ʼ��request�Լ�response����request��response��processor�������������߼������ݴ���
 */
public class ProcessDispatcher {
	private Request request;
    
    private Response response;
    
    private Processor processor;
    
    public ProcessDispatcher(InputStream input, OutputStream output) {
        init(input, output);
    }

    /**
             * ����input�Լ�output�����Request��Response�Լ�processor���г�ʼ��
     */
    private void init(InputStream input, OutputStream output) {
        Request request = new Request(input);
        request.resolve();        
        this.request = request;

        Response response = new Response(output);
        this.response = response;
        
        initProcessor(request);
    }
    
    private void initProcessor(Request request) {
        if(request.getUrl() != null && -1 != request.getUrl().indexOf("dynamic")) {
            DynamicServletProcessor dynamicProcessor = new DynamicServletProcessor();
            this.processor = dynamicProcessor;
            return ;
        }
        
        if(request.getUrl() != null && -1 != request.getUrl().indexOf("static")) {
            StaticServletProcessor staticProcessor = new StaticServletProcessor();
            this.processor = staticProcessor;
            return ;
        }
        
        return ;
    }

    /**
     * processor����Ҫ���þ��Ƿַ����󵽵����ɶ�̬servlet��������ֱ���Ҿ�̬��Դ
     * @throws IOException 
     */
    public void service() throws IOException {
        if(processor == null) {
            return ;
        }
        
        //����url��ȡ�����servletName
        request.setServletName(resolveServletName(request.getUrl()));
        processor.process(request, response);
    }

    private String resolveServletName(String url) {
        String[] arr = url.split("/");
        for(String s: arr) {
            if(s.equals("") || s.equals("dynamic") || s.equals("static")) {
                continue;
            }
            
            return s;
        }
        return "";
    }
}

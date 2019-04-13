package com.ty.dispatcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;
import com.ty.process.Processor;
import com.ty.process.impl.DynamicServletProcessor;
import com.ty.process.impl.StaticServletProcessor;

/** 此类作为一个请求处理分发器，根据客户端请求url的类型(包括静态资源以及动态资源请求)，去初始化不同的processor
 * 并且在此时初始化request以及response对象，request、response、processor构成整个处理逻辑与数据传输
 */
public class ProcessDispatcher {
	private Request request;
    
    private Response response;
    
    private Processor processor;
    
    public ProcessDispatcher(InputStream input, OutputStream output) {
        init(input, output);
    }

    /**
             * 根据input以及output对象对Request、Response以及processor进行初始化
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
     * processor的主要作用就是分发请求到底是由动态servlet处理，还是直接找静态资源
     * @throws IOException 
     */
    public void service() throws IOException {
        if(processor == null) {
            return ;
        }
        
        //根据url获取处理的servletName
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

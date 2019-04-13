package com.ty.process;

import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;

public interface Processor {
	//需替换成您本地txt路径的前缀
	static final String prefix = "E:\\workspace\\TomcatPro\\webroot\\";
	
	void process(Request request, Response response);
}

package com.ty.process;

import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;

public interface Processor {
	//���滻��������txt·����ǰ׺
	static final String prefix = "E:\\workspace\\TomcatPro\\webroot\\";
	
	void process(Request request, Response response);
}

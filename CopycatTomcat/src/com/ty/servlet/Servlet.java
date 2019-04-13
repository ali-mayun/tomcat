package com.ty.servlet;

import com.ty.httpEntity.Request;
import com.ty.httpEntity.Response;

public interface Servlet {

	public void process(Request request, Response response);
}

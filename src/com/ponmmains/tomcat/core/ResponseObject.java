package com.ponmmains.tomcat.core;

import java.io.PrintWriter;

import javax.servlet.ServletResponse;

/**
 * 负责封装响应参数对象
 * @author ponmmains
 * @version	1.0
 * @since	1.0
 *
 */
public class ResponseObject implements ServletResponse{
	private PrintWriter out;
	public void setWriter(PrintWriter out){
		this.out = out;
	}
	
	public PrintWriter getWriter(){
		return out;
	}
}

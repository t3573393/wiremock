package com.tomakehurst.wiremock.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tomakehurst.wiremock.mapping.Request;
import com.tomakehurst.wiremock.mapping.RequestHandler;
import com.tomakehurst.wiremock.mapping.Response;

public class HandlerDispatchingServlet extends HttpServlet {

	private static final long serialVersionUID = -6602042274260495538L;
	
	private RequestHandler requestHandler;
	private ResponseRenderer responseRenderer;
	
	@Override
	public void init(ServletConfig config) {
		ServletContext context = config.getServletContext();
		requestHandler = (RequestHandler) context.getAttribute(RequestHandler.CONTEXT_KEY);
		responseRenderer = (ResponseRenderer) context.getAttribute(ResponseRenderer.CONTEXT_KEY);
	}

	@Override
	protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		Request request = new HttpServletRequestAdapter(httpServletRequest);
		Response response = requestHandler.handle(request);
		responseRenderer.render(response, httpServletResponse);
	}
}
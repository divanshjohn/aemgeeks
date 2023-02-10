package com.honeywell.aem.core.servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.honeywell.aem.core.services.ProductDetailsRestclient;


@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=HTTP servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=sling/servlet/default",
		"sling.servlet.selectors=sample",
		 "sling.servlet.extension=json"	})
public class HttpServletDemo extends SlingSafeMethodsServlet {
	
	
	
	private static final long serialVersionUID = -3441313446148106321L;

	private static final Logger log = LoggerFactory.getLogger(HttpServletDemo.class);
	
	@Reference
	private ProductDetailsRestclient httpService;
	
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		
		try {
		
		String jsonResponse = httpService.makeHttpCalll();
		
		
		response.getWriter().println(jsonResponse);
		
		}  catch (URISyntaxException e) {
			
			log.error("Malformed URL Exception Occured"+e.getMessage());
			
		} 
	}

}

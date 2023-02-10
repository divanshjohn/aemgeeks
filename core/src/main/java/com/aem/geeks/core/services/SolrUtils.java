package com.honeywell.aem.core.services;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.LoggerFactory;

public final class SolrUtils {
	private static final org.slf4j.Logger LOG= LoggerFactory.getLogger(SolrUtils.class);
	
	private static int ConnectionTimeout = 50000;
	
	private static int socketTimeout = 60000;
	
	public static SolrClient getSolrClient(String solrUrl) {
		try {
			return new HttpSolrClient.Builder(solrUrl)
				    .withConnectionTimeout(ConnectionTimeout)
				    .withSocketTimeout(socketTimeout)
				    .build();
			
		}
		
		catch(Exception e) {
			LOG.info("\n Error in Utils -{}",e.getMessage());
	}
 return null;
}
	public static String getPageName(String id)
	{
		String pageName=StringUtils.replaceAll(id, "/","-");
		return pageName;
	}
}

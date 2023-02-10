package com.aem.geeks.core.services;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.httpcache.util.CacheUtils;

public class SolrSearchHelper {

	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SolrSearchHelper.class);
	private SolrCAConfig solrCAConfig;
	private SolrConfigurationService solrConfigurationService;
	private SolrServiceManager solrServiceManger;
	private SlingHttpServletRequest request;
	private SlingHttpServletResponse response;
	private String sitePath;
	
	public SolrSearchHelper( SlingHttpServletRequest request,
			SlingHttpServletResponse response,SolrServiceManager solrServiceManger) {
		
		this.solrServiceManger = solrServiceManger;
		this.request = request;
		this.response = response;
		this.sitePath = request.getParameter("sitePath");
		this.solrCAConfig = CAUtils.getContextAwareConfig(sitePath,request.getResourceResolver(),SolrCAConfig.class);
	}
	
	public SolrSearchHelper( String sitePath,
			SolrServiceManager solrServiceManger,ResourceResolver resourceResolver) {
		
		this.solrServiceManger = solrServiceManger;
		this.sitePath = sitePath;
		this.solrCAConfig = CAUtils.getContextAwareConfig(sitePath,resourceResolver,SolrCAConfig.class);
	}
	public void searchSolr(PageService pageService,SolrService solrService) {
		
		JSONArray pagesArray = new JSONArray();
		
		try {
			final ResourceResolver resourceResolver = this.request.getResourceResolver();
			solrConfigurationService=solrServiceManger.getServiceConfiguration(solrCAConfig.siteId());
			String searchOperation = request.getParameter("searchOperation");
			if(StringUtils.equalsIgnoreCase(searchOperation, "index"));
			{
				List<PageDetails> pageDetails = pageService.getPagesDetail(sitePath);
				String documentAdded = solrService.addDocument(pageDetails, this);
				response.getWriter().write(documentAdded);
			}
			else if(StringUtils.equalsIgnoreCase(searchOperation, "delete")) 
			{
				solrService.deleteIndex(this);
				response.getWriter().write("Document Deleted for"+solrCAConfig.siteId());
			}
			else if(StringUtils.equalsIgnoreCase(searchOperation, "search"))
					{
				String searchKey = request.getParameter("searchKey");
				String searchText = StringUtils.isNotBlank(searchKey) ? searchKey : null;
				response.getWriter().write(solrService.getSearchResult(searchText,this).toString());
			}
			else {
				response.getWriter().write("Please choose an option for Solr");
			}
			response.setContentType("application/json");
		}
		catch(Exception e) {
			
			
		}
	}
	public void solrIndex(PageService pageService, SolrService solrService) {
		JSONArray pageArray = new JSONArray();
		try {
			LOG.info(""+solrCAConfig.siteId(),solrCAConfig.CollectionName());
			solrConfigurationService=solrServiceManager.gerServiceConfiguration(solrCAConfig.siteId());
			List<PageDetails> pageDetails=pageService.getPagesDetail(sitePath);
			solrService.addDocument(pageDetails, this);
			
		}catch(Exception e) {
			
		}
		
	}
}

package com.honeywell.aem.core.services;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service =SolrService.class, immediate=true)
public class SolrServiceImpl implements SolrService{
	private static final Logger LOG = LoggerFactory.getLogger(SolrService.class);

	
	@Override
	public String addDocument(List<PageDetails> pageDetails,SolrSearchHelper solrSearchHelper) 
	{
	try{
		final String SolrServer = solrSearchHelper.getSolrConfigurationService.getSolrServerEndPrint();
		final SolrClient client = SolrUtils.getSolrClient(solrServer);
		for (PageDetails page: pageDetails) {
			
			final SolrInputDocument doc= new SolrInputDocument();
			doc.addFiled(SolrConstants.PAGE_ID,SolrUtils.getPageName(page.getPath()));
			doc.addFiled(SolrConstants.PAGE_NAME,page.getName());
			doc.addFiled(SolrConstants.PAGE_TITLE,page.getTitle());
			doc.addFiled(SolrConstants.PAGE_DESCRIPTION,page.getDescription());
			final UpdateResponse updateResponse = client.add(SolrSearchHelper.getSolrCAConfig().collectionName(), doc);
		}
		client.commit(SolrSearchHelper.getSolrCAConfig().collectionName());
		return "Total Size"+pageDetails.size()+"Document Added";
		
	}
	catch (Exception e) {
		LOG.error("\n ERROR While Indexing",e.getMessage());
	}
	
return "Documents Could not add into Solr";
}
	@Override
	public JSONArray  getSearchResult(String searchText, SolrSearchHelper solrSearchHelper)
	{
		JSONArray searchResult = new JSONArray();
		try {
			final String solrServer = solrSearchHelper.getSolarConfigurationService().getSolrServerEndPrint();
			final SolrClient solrClient = SolrUtils.getSolrClient(solrServer);
			String query = StringUtils.isNotBlank(searchText) ? searchText : "*.*";
			final SolrQuery solrQuery = new SolrQuery(query);
		    final QueryResponse response = solrClient.query(solrSearchHelper.getSolrCAConfig().collectionName(), solrQuery);
		    final SolrDocumentList documents =response.getResults();
		    LOG.info("\n Found {} Documnets."+documents.getNumFound());
		    for (SolrDocument document : documents) {
		    	
		    	JSONObject result = new JSONObject();
		    	result.put(SolrConstants.PAGE_ID, document.getFirstValue(SolrConstants.PAGE_ID));
		    	result.put(SolrConstants.PAGE_NAME, document.getFirstValue(SolrConstants.PAGE_NAME)
		    	result.put(SolrConstants.PAGE_TITLE, document.getFirstValue(SolrConstants.PAGE_TITLE));
		    	result.put(SolrConstants.PAGE_DESCRIPTION, document.getFirstValue(SolrConstants.PAGE_DESCRIPTION));
		    	searchResult.put(result);x
		    }
		}catch(Exception e) {
			
			LOG.error("Error while Search {}"+e.getMessage());
		}
		
		return searchResult;
		
		
	}
	@Override
	public void deleteIndex(SolrSearchHelper solrSearchHelper) {
		// TODO Auto-generated method stub
		
	}

}

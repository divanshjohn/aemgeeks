package com.aem.geeks.core.services;

import java.util.List;

import org.json.JSONArray;



public interface SolrService {

//String addDocument();

	

	String addDocument(List<PageDetails> pageDetails, SolrSearchHelper solrSearchHelper);

	void deleteIndex(SolrSearchHelper solrSearchHelper);

	public JSONArray  getSearchResult(String searchText, SolrSearchHelper solrSearchHelper);

}

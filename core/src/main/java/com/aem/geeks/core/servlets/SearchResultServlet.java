package com.adobe.training.core.servlets;

import com.day.cq.search.PredicateConverter;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/searchFulltext",
                "sling.servlet.methods=GET"
        })
public class SearchResultServlet extends SlingAllMethodsServlet {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 8376498384945866786L;
	public static final String PREDICATE_FULLTEXT = "fulltext";
    public static final String PREDICATE_TYPE = "type";
    public static final String PREDICATE_PATH = "path";
 
    public static final String UTF8 = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";
    protected static final String DEFAULT_SELECTOR = "search";
    protected static final String PARAM_FULLTEXT = "fulltext";
    private static final String PARAM_RESULTS_OFFSET = "resultsOffset";
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchResultServlet.class);
 
    @Reference
    private QueryBuilder queryBuilder;
 
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        try {
            JSONObject jsonObject = getResults(request);
            response.setContentType(APPLICATION_JSON);
            response.setCharacterEncoding(UTF8);
            response.getWriter().print(jsonObject);
        } catch (IOException | JSONException e) {
            LOGGER.error(e.getMessage());
        }
    }
 
 
    private JSONObject getResults(SlingHttpServletRequest request) throws JSONException {
        int searchTermMinimumLength = 3;
        int resultsSize = 10; //For pagination
        String searchRoot = "/content/";//request.getResource().getPath();
        JSONObject response = new JSONObject();
        JSONArray results = new JSONArray();
        String fulltext = request.getParameter(PARAM_FULLTEXT);
        if (fulltext == null || fulltext.length() < searchTermMinimumLength) {
            return response;
        }
        long resultsOffset = 0; //For pagination
        if (request.getParameter(PARAM_RESULTS_OFFSET) != null) {
            resultsOffset = Long.parseLong(request.getParameter(PARAM_RESULTS_OFFSET));
        }
        Map<String, String> predicatesMap = new HashMap<>();
        predicatesMap.put(PREDICATE_FULLTEXT, fulltext);
        predicatesMap.put(PREDICATE_PATH, searchRoot);
        predicatesMap.put(PREDICATE_TYPE, NameConstants.NT_PAGE);
 
        PredicateGroup predicates = PredicateConverter.createPredicates(predicatesMap);
        ResourceResolver resourceResolver = request.getResource().getResourceResolver();
        Query query = queryBuilder.createQuery(predicates, resourceResolver.adaptTo(Session.class));
        query.setHitsPerPage(resultsSize);
        if (resultsOffset != 0) {
            query.setStart(resultsOffset);
        }
        SearchResult searchResult = query.getResult();
        long totalMatches = searchResult.getTotalMatches();
 
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        List<Hit> hits = searchResult.getHits();
        if (hits != null && pageManager != null) {
            for (Hit hit : hits) {
                try {
                    JSONObject result = new JSONObject();
                    Resource hitRes = hit.getResource();
                    Page page = pageManager.getContainingPage(hitRes);
                    if (page != null) {
                        result.put("title", page.getTitle());
                        result.put("url", resourceResolver.map(page.getPath()) + ".html");
                        results.put(result);
                    }
                } catch (RepositoryException e) {
                    LOGGER.error("Unable to retrieve search results for query.", e);
                }
            }
        }
        response.put("totalMatches", totalMatches);
        response.put("results", results);
        return response;
    }
 
}

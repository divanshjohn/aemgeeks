package com.aem.geeks.core.servlets;;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.honeywell.aem.core.lists.models.QueryListModel;
import com.honeywell.aem.core.lists.services.ListService;
import com.honeywell.aem.core.models.BasePageModel;
import org.apache.http.entity.ContentType;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Component(
        service = {Servlet.class},
        property = {
        		"sling.servlet.patsh=" + "/bin/aemsolrsearch",
                "sling.servlet.methods=GET"
                
        }
)
public class GeekSolrSearch extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(GeekSolrSearch.class);
    
    @Reference
    SolrService solrService;
    
    @Reference
    PageService pageService;

    @Reference
    SolrServiceManager solrServiceManager;
    
   private SolrCAConfig solrCAConfgi;
    

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {

        SolrSearchHelper solrSearchHelper = new SolrSearchHelper(request,response,solrServiceManager);
        solrSearchHelper.searchSolr(pageService, solrService);
    }
    //git hub user name= divanshjohn/Digaldigal12@
   // gmail->divanshdigal@gmail.com/digaldigal12@
   // gmail->divanshjohn@gmail.com/Divansh12@
}

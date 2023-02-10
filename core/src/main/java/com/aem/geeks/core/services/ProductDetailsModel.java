package com.aem.geeks.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.day.cq.wcm.api.Page;


@Model(adaptables =SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetailsModel {
	
    @ValueMapValue
    private Integer pageSize;

    @ScriptVariable
    private Page currentPage;
    private String pagePath;  


    public Integer getPageSize() {
        return pageSize;
    }

    public String getPagePath() {
    	pagePath=currentPage.getPath();
        return pagePath;
      
    }
    
  
}
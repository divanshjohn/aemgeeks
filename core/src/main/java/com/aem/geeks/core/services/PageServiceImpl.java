package com.aem.geeks.core.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Page;


@Component(immediate=true,service = PageService.class)
public class PageServiceImpl implements PageService {
 
	@Reference
    ResourceResolver resourceResolver;
	@Reference
    ResourceResolverFactory resourceResolverFactory;
	
	@Override
	public List<PageDetails> getPagesDetail(String sitePath) {
		
		try {
			
			//final ResourceResolver rr =resourceResolverFactory.getServiceResourceResolver(null)
			Page page=resourceResolver.adaptTo(PageManager.class).getPage(sitePath);
			Iterator<Page> it=page.listChildren(null,true);
			List<PageDetails> pageDetails = new ArrayList<>();
			while(it.hasNext()) {
				
				Page childPage= it.next();
				String title= StringUtils.isNotBlank(childPage.getTitle())? childPage.getTitle() :childPage.getName();
				String name=childPage.getName();
				String description= StringUtils.isNotBlank(childPage.getDescription())? childPage.getDescription() :childPage.getName();
				String path=childPage.getPath();
				pageDetails.add(new PageDetails(title,name,description,path));
			}
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

}

package com.honeywell.aem.core.services;

import java.util.logging.Logger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.LoggerFactory;

//import com.honeywell.aem.core.services.SolrConfigurationService;

@Component(service =SolrConfigurationService.class, immediate=true)
@Designate(ocd = SolrOSGiConfig.class, factory=true)

public class SolrConfigurationServiceImpl implements SolrConfigurationService {
	
	private static org.slf4j.Logger LOG= LoggerFactory.getLogger(SolrConfigurationServiceImpl.class);
	
	private String siteId;
	private String solrServerEndPrint;
	private String solrUserName;
	private String solrPassoword;
	private String[] solrResponseFields;
	
	@Activate
	@Modified
	protected void activate(final SolrOSGiConfig config) {
		 this.siteId=config.siteId();
		 this.solrServerEndPrint=config.solrServerEndPrint();
		 this.solrUserName=config.solrUserName();
		 this.solrPassoword=config.solrPassword();
		this.solrResponseFields=config.solrResponseFields();
		
	}
	@Override
	 public String getSiteId() {
		 
		 return siteId;
	 }
	@Override
   public String getSolrServerEndPrint() {
		 
		 return solrServerEndPrint;
	 }
	@Override
   public String getSolrUserName() {
	 
	 return solrUserName;
	 
 }
	@Override
 public String getSolrPassoword() {
	 
	 return solrPassoword;
 }
	@Override
 public String[] getSolrResponseFields() {
	 
	 return solrResponseFields;
 }
}

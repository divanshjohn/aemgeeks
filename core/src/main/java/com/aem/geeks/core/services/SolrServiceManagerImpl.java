package com.honeywell.aem.core.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.LoggerFactory;

@Component(immediate=true,service = SolrServiceManager.class)
public class SolrServiceManagerImpl implements SolrServiceManager{
	private static final org.slf4j.Logger LOGGER= LoggerFactory.getLogger(SolrServiceManagerImpl.class);
	
 private Map<String,SolrConfigurationService> configurationList;
 
 @Reference(service = SolrConfigurationService.class, cardinality = ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC)
 
 protected synchronized void bindApiServiceConfiguration(final SolrConfigurationService config)
 {
	 LOGGER.info("\n==== unbindApiServiceConfiguration: "+config.getSiteId());
	 if(configurationList == null)
	 {
		 configurationList=new ConcurrentHashMap<>();
	 }
	 configurationList.put(config.getSiteId(),config);
 }
 
 protected synchronized void unbindApiServiceConfiguration(final SolrConfigurationService config)
 {
	 
	 LOGGER.info("\n==== unbindApiServiceConfiguration: "+config.getSiteId());
	 configurationList.remove(config.getSiteId());
	 
 }
 @Override
 public SolrConfigurationService getServiceConfiguration(String siteId) {return configurationList.get(siteId);}
}

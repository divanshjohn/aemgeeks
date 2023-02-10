package com.honeywell.aem.core.services;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label="AEM Integration- Context Aware Solr Configuration", collection=true)
public @interface SolrCAConfig {
	
	@Property(label="Site Id",description="Site Id for Specific Site")
	String siteId();
	
	@Property(label="Core Name",description="Core Name for Site")
	String CollectionName();
	
	@Property(label="Field Names",description="Site Id for Specific Site")
	String[] filedNames() default {StringUtils.EMPTY};



}

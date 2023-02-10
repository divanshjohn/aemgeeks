package com.aem.geeks.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
 

@ObjectClassDefinition(name = "AEM Integration-OSGi SOLR Configuration")
public @interface SolrOSGiConfig {
 
     
    @AttributeDefinition(name = "Site Id")
    String siteId();
    
    @AttributeDefinition(name = "Solar Server solrServerEndPrint")
    String solrServerEndPrint();
    
    @AttributeDefinition(name = "Solar Server UserName")
    String solrUserName();
    
    @AttributeDefinition(name = "Solar Server Password")
    String solrPassword();
    
    @AttributeDefinition(name = "Solar Response Fileds")
    String[] solrResponseFields();
   
}
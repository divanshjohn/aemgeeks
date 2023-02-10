package com.honeywell.aem.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
 

@ObjectClassDefinition(name = "Product Details RestClient Configuration")
public @interface RestClientConfiguration {
 
     
    @AttributeDefinition(name = "Endpoint", description = "Rest service endpoint")
    String getEndpoint();
 
   //https://dummyjson.com/products
}
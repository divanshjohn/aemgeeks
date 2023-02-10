package com.honeywell.aem.core.services.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.honeywell.aem.core.services.GsonProvider;
import com.honeywell.aem.core.services.ProductDetailsRestclient;
import com.honeywell.aem.core.services.RestClientConfiguration;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URISyntaxException;
import org.json.JSONException;


@Component(
        immediate = true,
        service = ProductDetailsRestclient.class
)
@Designate(ocd = RestClientConfiguration.class)
public class ProductDetailsRestclientImpl implements ProductDetailsRestclient{
 
    public static final String APPLICATION_JSON = "application/json";
    public static final String UTF8 = "UTF-8";
 
    private Logger LOG = LoggerFactory.getLogger(getClass());
 
    @Reference
	private GsonProvider gsonProvider;

    
    HttpGet httpget = null;
	
	private RestClientConfiguration configuration;
	 JsonObject response;
	
	@Activate
	protected void activate(RestClientConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Overridden method of the HttpService
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Override
	public String makeHttpCalll() throws URISyntaxException , IOException {

		LOG.error("Reading the config values");

		try {

			 String url = configuration.getEndpoint();
			 CloseableHttpClient httpclient = HttpClients.createDefault();
		         httpget = new HttpGet(url);
		         CloseableHttpResponse closeableresponse = httpclient.execute(httpget);
		         String resp = EntityUtils.toString(closeableresponse.getEntity(), UTF_8);
		         response = this.gsonProvider.gson().fromJson(resp, JsonObject.class);
		       	       			
				LOG.info(response.toString());
				
		}	catch(IllegalArgumentException e){
			
			LOG.error("Exception occured Malformed API URL"+e.getMessage());
		}	
		finally {
            httpget.releaseConnection();
           
        }
	return response.toString();
		
			}
 
}
 
 
  

package com.adobe.training.core.servlets;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import javax.servlet.Servlet;
import org.osgi.framework.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
//import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.sitemap.builder.Url.ChangeFrequency;
import org.apache.sling.sitemap.spi.common.SitemapLinkExternalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.sling.api.resource.ValueMap;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import java.util.Calendar;
import org.apache.commons.lang3.time.FastDateFormat;

@Component(service=Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=demo Sitemap Servlet",
                "sling.servlet.paths="+ "/bin/demo/sitemapServlet",
                "sling.servlet.extensions="+ "xml"
            })
public class DemoSitemapServlet extends SlingAllMethodsServlet{
	
	private static final long serialVersionUID = -2788393487421811579L;
    
	private String characterEncoding;
	
	private String externalizerDomain;
	 private String ALWAYS;
    
	private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

	
	@Reference
    private ResourceResolverFactory resolverFactory;
	
	 @Reference
	 private Externalizer externalizer;

	 @Reference
	    private SitemapLinkExternalizer externalizer1;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoSitemapServlet.class);
	
	private static final String NS = "http://www.sitemaps.org/schemas/sitemap/0.9";
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType(request.getResponseContentType());
		response.setContentType("application/xml");
        if (StringUtils.isNotEmpty(this.characterEncoding)) {
            response.setCharacterEncoding(characterEncoding);
        }
        ResourceResolver resourceResolver = request.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page page = pageManager.getPage("/content/demo/en");
        String path = resourceResolver.getResource("/content/demo/en").getPath(); 

LOGGER.info("PAGE PATH1"+path);
LOGGER.info("PAGE PATH2"+page.getPath());
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        try {
            XMLStreamWriter stream = outputFactory.createXMLStreamWriter(response.getWriter());
            stream.writeStartDocument("1.0");

            stream.writeStartElement("", "urlset", NS);
            stream.writeNamespace("", NS);

            // first do the current page
            write(page, stream, resourceResolver);

            for (Iterator<Page> children = page.listChildren(new PageFilter(false, true), true); children
                    .hasNext();) {
                write(children.next(), stream, resourceResolver);
            }

           
            stream.writeEndElement();

            stream.writeEndDocument();
        } catch (XMLStreamException e) {
            throw new IOException(e);
        }
    }

	private void write(Page page, XMLStreamWriter stream, ResourceResolver resolver) throws XMLStreamException {
       
        stream.writeStartElement(NS, "url");
        String loc = "";

        
            //loc = externalizer.externalLink(resolver, externalizerDomain, String.format("%s.html", page.getPath()));
       
       loc =page.getPath();
       String prop=page.getProperties().get("jcr:title").toString();
       
       
        

        writeElement(stream, "loc", loc);

       
            Calendar cal = page.getLastModified();
            if (cal != null) {
                writeElement(stream, "lastmod", DATE_FORMAT.format(cal));
                writeElement(stream, "priority", prop);
                //url.setPriority(20);
                writeElement(stream, "frequency", DATE_FORMAT.toString());
            }
        
             
         
//            ValueMap properties = page.getProperties();
//            writeFirstPropertyValue(stream, "changefreq", changefreqProperties, properties);
//            writeFirstPropertyValue(stream, "priority", priorityProperties, properties);
       

        stream.writeEndElement();
    }
	private void writeElement(final XMLStreamWriter stream, final String elementName, final String text)
            throws XMLStreamException {
        stream.writeStartElement(NS, elementName);
        stream.writeCharacters(text);
        stream.writeEndElement();
    }


}

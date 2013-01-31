package org.espressoOtr.exs.api.daum;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.espressoOtr.exs.api.ApiKey;
import org.espressoOtr.exs.api.SearchApi;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;
import org.espressootr.lib.string.StringAppender;
import org.espressootr.lib.utils.InitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DaumApi implements SearchApi
{
    
    private String uri = InitUtil.EMPTY_STRING;
    
    private DaumApiTarget target;
    
    private List<SearchResult> searchResultList = Collections.emptyList();
    
    private ApiKey apiKey = null;
    
    private String sort = "date";
    
    private int outputCountLimit = 50;
    
    private int outputCountDefault = 10;
    
    private int pageNoLimit = 500;
    
    private int pageNoDefault = 1;
    
    private int outputCount = outputCountDefault; // default.
    
    private int pageNo = pageNoDefault; // default
    
    Logger logger = LoggerFactory.getLogger(DaumApi.class);
    
    public DaumApi(ApiKey daumApiKey)
    {
        this.apiKey = daumApiKey;
    }
    
    public DaumApi()
    {
        this.apiKey = new ApiKey(this);
    }
    
    public void setTarget(DaumApiTarget target)
    {
        
        this.target = target;
    }
    
    public DaumApiTarget getTarget()
    {
        return this.target;
        
    }
    
    public String getTargetString()
    {
        String targetString = InitUtil.EMPTY_STRING;
        
        if (getTarget() == DaumApiTarget.BLOG)
        {
            targetString = "blog";
        }
        else if (getTarget() == DaumApiTarget.CAFE)
        {
            targetString = "cafe";
        }
        else if (getTarget() == DaumApiTarget.WEB)
        {
            targetString = "web";
        }
        
        return targetString;
        
    }
    
    public void request(String keyword)
    {
        
        logger.info(DaumApi.class.getName() + " KEYWORD : " + "'" + keyword + "'");
        
        try
        {
            requestDaumAPI(keyword);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void requestDaumAPI(String keyword) throws IOException, SAXException, ParserConfigurationException
    {
        uri = getURI(keyword);
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(uri);
        this.searchResultList = parseDOM(doc);
        
        logger.info("daum result count : " + this.searchResultList.size());
        
    }
    
    private String getURI(String keyword) throws IOException
    {
        
        String subUri = getSubURI();
        
        String createdUri = StringAppender.mergeToStr("http://apis.daum.net/search/", getTargetString(), "?q=", URLEncoder.encode(keyword, "UTF-8"), subUri, "&apikey=", this.apiKey.getKey()
        
        );
        
        // String createdUri = "http://apis.daum.net/search/" +
        // getTargetString() + "?q=" + URLEncoder.encode(keyword, "UTF-8") +
        // this.getSubURI()
        // + "&apikey=" + this.apiKey.getKey();
        
        return createdUri;
    }
    
    private String getSubURI()
    {
        
        StringBuilder subUriSb = new StringBuilder();
         
        
        subUriSb.append("&reuslt");
        subUriSb.append(String.valueOf(this.outputCount));
        subUriSb.append("&pageno=");
        subUriSb.append(String.valueOf(this.pageNo));
        
        if (getTarget() == DaumApiTarget.BLOG)
        {
            // subURI = "&result=" + String.valueOf(this.outputCount) +
            // "&pageno=" + String.valueOf(this.pageNo) + "&sort=" + sort +
            // "&output=" + output;
            subUriSb.append("&sort=");
            subUriSb.append(sort);
            
        }
        else if (getTarget() == DaumApiTarget.CAFE)
        {
            // subURI = "&result=" + String.valueOf(this.outputCount) +
            // "&pageno=" + String.valueOf(this.pageNo) + "&sort=" + sort +
            // "&output=" + output;
            subUriSb.append("&sort=");
            subUriSb.append(sort);
            
        }
        
        subUriSb.append("&output=xml");
        
        return subUriSb.toString();
    }
    
    private List<SearchResult> parseDOM(Document doc)
    {
        List<SearchResult> searchResultList = new ArrayList<SearchResult>();
        
        Element root = doc.getDocumentElement();
        NodeList list = root.getElementsByTagName("item");
        
        for (int i = 0; i < list.getLength(); i++)
        {
            Element element = (Element) list.item(i);
            
            TextSearchResult searchResult = new TextSearchResult();
            
            searchResult.setTitle(getContent(element, "title"));
            searchResult.setLink(getContent(element, "link"));
            searchResult.setSnippet(getContent(element, "description"));
            
            searchResultList.add(searchResult);
            
        }
        
        return searchResultList;
    }
    
    private String getContent(Element element, String tagName)
    {
        
        NodeList list = element.getElementsByTagName(tagName);
        Element cElement = (Element) list.item(0);
        
        if (cElement.getFirstChild() != null)
        {
            return cElement.getFirstChild().getNodeValue();
        }
        else
        {
            return "";
        }
    }
    
    public List<SearchResult> response()
    {
        
        logger.debug(DaumApi.class.getName() + " result : " + this.searchResultList.size());
        
        return this.searchResultList;
        
    }
    
    public String getAPIName()
    {
        return this.getClass().getName();
        
    }
    
    public String getSort()
    {
        return this.sort;
    }
    
    public void setSort(String sort)
    {
        this.sort = sort;
        
    }
    
    @Override
    public int getOutputCount()
    {
        return this.outputCount;
    }
    
    @Override
    public void setOutputCount(int outputCount)
    {
        
        if (outputCount > this.outputCountLimit)
        {
            this.outputCount = this.outputCountDefault;
        }
        else
        {
            this.outputCount = outputCount;
        }
    }
    
    @Override
    public int getPageNo()
    {
        return this.pageNo;
    }
    
    @Override
    public void setPageNo(int pageNo)
    {
        
        if (pageNo > this.pageNoLimit)
        {
            this.pageNo = this.pageNoDefault;
        }
        else
        {
            this.pageNo = pageNo;
        }
        
    }
}

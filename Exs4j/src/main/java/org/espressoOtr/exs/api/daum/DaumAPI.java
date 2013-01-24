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
import org.espressoOtr.exs.api.SearchAPI;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult; 
import org.espressootr.lib.utils.InitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DaumAPI implements SearchAPI
{
    
    private String uri = InitUtil.EMPTY_STRING;
    
    private DaumAPITarget target;
    
    private List<SearchResult> searchResultList = Collections.emptyList();
    private ApiKey apiKey = null;
    
    private String sort = "date";
    
    private int outputCountLimit = 50;
    private int outputCountDefault = 10;
    
    private int pageNoLimit = 500;
    private int pageNoDefault = 1;
    
    private int outputCount = outputCountDefault; // default.
    private int pageNo = pageNoDefault; // default
    

    Logger logger = LoggerFactory.getLogger(DaumAPI.class);
    
    
    public DaumAPI(ApiKey daumApiKey)
    {
        this.apiKey = daumApiKey;
    }
    
    public DaumAPI()
    {
        this.apiKey = new ApiKey(this);
    }
    
    public void SetTarget(DaumAPITarget target)
    {
        
        this.target = target;
    }
    
    public DaumAPITarget GetTarget()
    {
        return this.target;
        
    }
    
    public String GetTargetString()
    {
        String targetString = InitUtil.EMPTY_STRING;
        
        if (GetTarget() == DaumAPITarget.BLOG)
        {
            targetString = "blog";
        }
        else if (GetTarget() == DaumAPITarget.CAFE)
        {
            targetString = "cafe";
        }
        else if (GetTarget() == DaumAPITarget.WEB)
        {
            targetString = "web";
        }
        
        return targetString;
        
    }
    
    public void request(String keyword)
    {
        
        logger.info(DaumAPI.class.getName() + " KEYWORD : " + "'" + keyword + "'");
        
        try
        {
            RequestDaumAPI(keyword);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void RequestDaumAPI(String keyword) throws IOException, SAXException, ParserConfigurationException
    {
        uri = GetURI(keyword);
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(uri);
        this.searchResultList = parseDOM(doc);
        
        logger.info("daum result count : " + this.searchResultList.size());
        
    }
    
    private String GetURI(String keyword) throws IOException
    {
        String createdUri = "http://apis.daum.net/search/" + GetTargetString() + "?q=" + URLEncoder.encode(keyword, "UTF-8") + this.GetSubURI()
                + "&apikey=" + this.apiKey.getKey();
        
        return createdUri;
    }
    
    private String GetSubURI()
    {
        
        String subURI = InitUtil.EMPTY_STRING;
        String output = "xml";
        
        if (GetTarget() == DaumAPITarget.BLOG)
        {
            subURI = "&result=" + String.valueOf(this.outputCount) + "&pageno=" + String.valueOf(this.pageNo) + "&sort=" + sort + "&output=" + output;
            
        }
        
        else if (GetTarget() == DaumAPITarget.WEB)
        {
            subURI = "&result=" + String.valueOf(this.outputCount) + "&pageno=" + String.valueOf(this.pageNo) + "&output=" + output;
            
        }
        else if (GetTarget() == DaumAPITarget.CAFE)
        {
            subURI = "&result=" + String.valueOf(this.outputCount) + "&pageno=" + String.valueOf(this.pageNo) + "&sort=" + sort + "&output=" + output;
            
        }
        
        return subURI;
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
            
            searchResult.title = getContent(element, "title");
            searchResult.link = getContent(element, "link");
            searchResult.snippet = getContent(element, "description");
            
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
        
        logger.info(DaumAPI.class.getName() + " result : " + this.searchResultList.size());
        
        for (SearchResult sr : this.searchResultList)
        {
            sr.PrintResult();
        }
        
        return this.searchResultList;
        
    }
    
    public String GetAPIName()
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

package org.espressoOtr.exs.api.bing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;

import org.espressoOtr.exs.api.ApiKey;
import org.espressoOtr.exs.api.SearchAPI;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BingAPI implements SearchAPI
{
    
    private List<SearchResult> searchResultList = Collections.emptyList();
    
    private ApiKey apiKey = null;
    
    private int outputCountLimit = 50;
    
    private int outputCountDefault = 10;
    
    private int pageNoDefault = 1;
    
    private int outputCount = outputCountDefault;
    
    private int pageNo = pageNoDefault; // default
    
    Logger logger = LoggerFactory.getLogger(BingAPI.class);
    
    public BingAPI(ApiKey bingApiKey)
    {
        this.apiKey = bingApiKey;
    }
    
    public BingAPI()
    {
        this.apiKey = new ApiKey(this);
        System.out.println(this.apiKey.getKey());
    }
    
    public void request(String keyword)
    { 
        logger.info(BingAPI.class.getName() + " KEYWORD : " + keyword);
        
        try
        {
            RequestBingAPI(keyword);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void RequestBingAPI(String keyword)
    {
        
        AzureSearchWebQuery aq = new AzureSearchWebQuery();
        aq.setAppid(this.apiKey.getKey());
        aq.setQuery(keyword);
        
        aq.setPage(this.pageNo);
        aq.setPerPage(this.outputCount);
        
        aq.doQuery();
        
        AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
        
        searchResultList = parseResult(ars);
    }
    
    private List<SearchResult> parseResult(AzureSearchResultSet<AzureSearchWebResult> ars)
    {
        List<SearchResult> searchResultList = new ArrayList<SearchResult>();
        
        for (AzureSearchWebResult anr : ars)
        {
            TextSearchResult searchResult = new TextSearchResult();
            
            searchResult.title = anr.getTitle();
            searchResult.link = anr.getUrl();
            searchResult.snippet = anr.getDescription();
            
            searchResultList.add(searchResult);
            
        }
        
        return searchResultList;
    }
    
    public List<SearchResult> response()
    {
        logger.debug(BingAPI.class.getName() + " result : " + this.searchResultList.size());
        
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
    
    public int getOutputCount()
    {
        return this.outputCount;
    }
    
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
        
        this.pageNo = pageNo;
        
    }
    
}
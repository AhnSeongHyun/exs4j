package org.espressoOtr.exs.api.bing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;

import org.espressoOtr.exs.api.ApiKey;
import org.espressoOtr.exs.api.SearchApi;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BingApi implements SearchApi
{
    
    private List<SearchResult> searchResultList = Collections.emptyList();
    
    private ApiKey apiKey = null;
    
    private int outputCountLimit = 50;
    
    private int outputCountDefault = 10;
    
    private int pageNoDefault = 1;
    
    private int outputCount = outputCountDefault;
    
    private int pageNo = pageNoDefault; // default
    
    Logger logger = LoggerFactory.getLogger(BingApi.class);
    
    public BingApi(ApiKey bingApiKey)
    {
        this.apiKey = bingApiKey;
    }
    
    public BingApi()
    {
        this.apiKey = new ApiKey(this); 
    }
    
    public void request(String keyword)
    {
        logger.info(BingApi.class.getName() + " KEYWORD : " + keyword);
        
        try
        {
            requestBingAPI(keyword);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void requestBingAPI(String keyword)
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
            
            searchResult.setTitle(anr.getTitle());
            searchResult.setLink(anr.getUrl());
            searchResult.setSnippet(anr.getDescription());
            
            searchResultList.add(searchResult);
            
        }
        
        return searchResultList;
    }
    
    public List<SearchResult> response()
    {
        logger.debug(BingApi.class.getName() + " result : " + this.searchResultList.size());
        
        return this.searchResultList;
    }
    
    public String getAPIName()
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
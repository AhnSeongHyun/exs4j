package org.espressoOtr.exs.api;

import java.util.List;

import org.espressoOtr.exs.api.bing.BingAPI;
import org.espressoOtr.exs.api.daum.DaumAPI;
import org.espressoOtr.exs.api.daum.DaumAPITarget;
import org.espressoOtr.exs.api.naver.NaverAPI;
import org.espressoOtr.exs.api.naver.NaverAPITarget;
import org.espressoOtr.exs.api.result.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiManager
{
    SearchAPI api = null;
    
    int currentOutputCount = 5;
    Logger logger = LoggerFactory.getLogger(ApiManager.class);
    
    public ApiManager()
    {
        
    }
    
    public String[] splitDomain(String domain)
    {
        String[] domainSplitted = new String[2];
        
        StringBuffer buf = new StringBuffer();
        char[] domainChars = domain.toCharArray();
        
        for (int i = 0; i < domainChars.length; i++)
        {
            if (domainChars[i] == '.')
            {
                domainSplitted[0] = buf.toString();
                
                buf.delete(0, buf.length());
            }
            else
            {
                buf.append(domainChars[i]);
            }
        }
        
        domainSplitted[1] = buf.toString();
        
        return domainSplitted;
    }
    
    public void request(String domain, String keyword, int outputCount, int pageNo)
    {
        
        String[] domainSplitted = domain.split("[.]");
        
        String service = domainSplitted[0].trim();
        String target = "";
        
        if (domainSplitted.length > 1)
        {
            target = domainSplitted[1].trim();
        }
        
        logger.info("service :{} ", service);
        logger.info("target : {}", target);
        
        switchSearchAPI(service, target);
        currentOutputCount = outputCount;
        
        api.setOutputCount(outputCount);
        api.setPageNo(pageNo);
        api.request(keyword);
        
    }
    
    private void switchSearchAPI(String service, String target)
    {
        
        if (SearchEngines.NAVER.toString().equals(service.toUpperCase()))
        {
            api = setNaverSearchEngine(target);
        }
        else if (SearchEngines.DAUM.toString().equals(service.toUpperCase()))
        {
            api = setDaumSearchEngine(target);
        }
        else if (SearchEngines.BING.toString().equals(service.toUpperCase()))
        {
            api = setBingSearchEngine(target);
        }
        
        else
        {
            api = null;
        }
        
    }
    
    private static SearchAPI setNaverSearchEngine(String target)
    {
        NaverAPI naverApi = new NaverAPI();
        
        if (NaverAPITarget.BLOG.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverAPITarget.BLOG);
        }
        else if (NaverAPITarget.CAFEARTICLE.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverAPITarget.CAFEARTICLE);
        }
        else if (NaverAPITarget.NEWS.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverAPITarget.NEWS);
        }
        else if (NaverAPITarget.WEBKR.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverAPITarget.WEBKR);
        }
        else
        {
            naverApi.setTarget(NaverAPITarget.WEBKR);
        }
        
        return naverApi;
        
    }
    
    private static SearchAPI setDaumSearchEngine(String target)
    {
        DaumAPI daumApi = new DaumAPI();
        daumApi.setTarget(DaumAPITarget.WEB);
        
        if (DaumAPITarget.BLOG.toString().equals(target.toUpperCase()))
        {
            daumApi.setTarget(DaumAPITarget.BLOG);
        }
        else if (DaumAPITarget.CAFE.toString().equals(target.toUpperCase()))
        {
            daumApi.setTarget(DaumAPITarget.CAFE);
        }
        else if (DaumAPITarget.WEB.toString().equals(target.toUpperCase()))
        {
            daumApi.setTarget(DaumAPITarget.WEB);
        }
        else
        {
            daumApi.setTarget(DaumAPITarget.WEB);
        }
        
        return daumApi;
    }
    
    private static SearchAPI setBingSearchEngine(String target)
    {
        BingAPI bingApi = new BingAPI();
        return bingApi;
    }
    
    public List<SearchResult> response()
    {
        List<SearchResult> searchResult = api.response();
        
        if (searchResult.size() < currentOutputCount)
        {
            currentOutputCount = searchResult.size();
        }
        
        return searchResult.subList(0, currentOutputCount);
        
    }
}

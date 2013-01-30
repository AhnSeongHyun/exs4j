package org.espressoOtr.exs.api;

import java.util.HashMap;
import java.util.List;

import org.espressoOtr.exs.api.bing.BingApi;
import org.espressoOtr.exs.api.daum.DaumApi;
import org.espressoOtr.exs.api.daum.DaumApiTarget;
import org.espressoOtr.exs.api.naver.NaverApi;
import org.espressoOtr.exs.api.naver.NaverApiTarget;
import org.espressoOtr.exs.api.result.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiManager
{
    SearchApi api = null;
    
    int currentOutputCount = 5;
    
    Logger logger = LoggerFactory.getLogger(ApiManager.class);
    
    HashMap<String, SearchApi> apiMap = null;
    
    public ApiManager()
    {
        apiMap = new HashMap<String, SearchApi>(3);
        
        apiMap.put("NAVER", new NaverApi());
        apiMap.put("DAUM", new DaumApi());
        apiMap.put("BING", new BingApi());
    }
    
    public void request(String domain, String keyword, int outputCount, int pageNo) throws Exception
    {
        
        String[] domainSplitted = domain.split("[.]");
        
        String service = domainSplitted[0].trim();
        String target = "";
        
        if (domainSplitted.length > 1)
        {
            target = domainSplitted[1].trim();
        }
        else
        {
            throw new Exception("invalid domain");
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
    
    private SearchApi setNaverSearchEngine(String target)
    {
        NaverApi naverApi = (NaverApi) this.apiMap.get("NAVER");
        
        if (NaverApiTarget.BLOG.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverApiTarget.BLOG);
        }
        else if (NaverApiTarget.CAFEARTICLE.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverApiTarget.CAFEARTICLE);
        }
        else if (NaverApiTarget.NEWS.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverApiTarget.NEWS);
        }
        else if (NaverApiTarget.WEBKR.toString().equals(target.toUpperCase()))
        {
            naverApi.setTarget(NaverApiTarget.WEBKR);
        }
        else
        {
            naverApi.setTarget(NaverApiTarget.WEBKR);
        }
        
        return naverApi;
        
    }
    
    private SearchApi setDaumSearchEngine(String target)
    {
        DaumApi daumApi = (DaumApi) this.apiMap.get("DAUM");
        
        if (DaumApiTarget.BLOG.toString().equals(target.toUpperCase()))
        {
            daumApi.setTarget(DaumApiTarget.BLOG);
        }
        else if (DaumApiTarget.CAFE.toString().equals(target.toUpperCase()))
        {
            daumApi.setTarget(DaumApiTarget.CAFE);
        }
        else if (DaumApiTarget.WEB.toString().equals(target.toUpperCase()))
        {
            daumApi.setTarget(DaumApiTarget.WEB);
        }
        else
        {
            daumApi.setTarget(DaumApiTarget.WEB);
        }
        
        return daumApi;
    }
    
    private SearchApi setBingSearchEngine(String target)
    {
        BingApi bingApi = (BingApi) this.apiMap.get("BING");
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

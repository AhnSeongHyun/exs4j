package org.espressoOtr.server.search;

import java.util.ArrayList; 
import java.util.List;

import org.espressoOtr.exs.api.manager.ApiManager;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;
import org.espressoOtr.exs.index.Barista;
import org.espressoOtr.exs.server.params.ExsRequestParam;
import org.espressoOtr.exs.server.params.ExsResponseParam; 
import org.espressoOtr.exs.sql.SqlSearchResultTable;
import org.espressoOtr.exs.sql.param.SearchResultRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchManager
{
    Logger logger = LoggerFactory.getLogger(SearchManager.class);
      
    Barista baristaIndex = Barista.getInstance();
    
    ApiManager apiManager = null;
    
    public SearchManager()
    {
        apiManager = new ApiManager();
    }
    
    /***
     * 1) Search from Internal index and DB. 
     * 2) Search from OpenAPI. 
     * @param ExsRequestParam  
     * @return ExsResponseParam
     * @throws Exception
     */
    public ExsResponseParam search(ExsRequestParam exsReqParam) throws Exception
    {
        
        ExsResponseParam exsResParam = new ExsResponseParam();
        
        List<SearchResult> searchResult = new ArrayList<SearchResult>();
        List<String> reqCodeList = getRequestCode(exsReqParam.getKeyword());
        
        logger.info("reqCodeList : {}", reqCodeList);
        
        if (reqCodeList.size() == 0)
        {
            logger.info("Get Data from OPEN API");
            apiManager.request(exsReqParam.getDomain(), exsReqParam.getKeyword(), exsReqParam.getOutputCount(), exsReqParam.getPageNo());
            
            searchResult = apiManager.response();
            
        }
        else
        {
            logger.info("Get Data from DB");
            
            for (String requestCode : reqCodeList)
            {
                List<SearchResultRecord> sqlSearchResultData = SqlSearchResultTable.select(requestCode);
                
                for (SearchResultRecord srr : sqlSearchResultData)
                {
                    TextSearchResult textSearchResult = new TextSearchResult();
                    textSearchResult.setTitle(srr.getTitle());
                    textSearchResult.setSnippet(srr.getSnippet());
                    textSearchResult.setLink(srr.getLink());
                    
                    searchResult.add(textSearchResult);
                }
            }
        }
        
        exsResParam.setOutputCount(searchResult.size());
        exsResParam.setResultList(searchResult);
        
        return exsResParam;
        
    }
    
    /***
     * Get Intersected ReqeustCode from Barista Index. 
     * @param searchKeyword
     * @return List<String> requestCodeList
     */
    private List<String> getRequestCode(String searchKeyword)
    {
        String[] kwds = searchKeyword.split(" ");
        
        IntersectionMap ictMap = new IntersectionMap();
        
        for (String kwd : kwds)
        {
            List<String> reqCodes = baristaIndex.getRequestCodes(kwd.trim());
            
            for (String reqCode : reqCodes)
            {
                ictMap.add(reqCode);
            }
        }// for
        
        return ictMap.getDescKeyList();
        
    }
    
}

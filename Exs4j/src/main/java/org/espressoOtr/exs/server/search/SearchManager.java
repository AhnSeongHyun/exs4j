package org.espressoOtr.exs.server.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.espressoOtr.exs.api.manager.ApiManager;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;
import org.espressoOtr.exs.cmd.CommandType;
import org.espressoOtr.exs.index.Barista;
import org.espressoOtr.exs.localcache.StoringCache;
import org.espressoOtr.exs.messageq.MessageQueue;
import org.espressoOtr.exs.server.params.ExsRequestParam;
import org.espressoOtr.exs.server.params.ExsResponseParam;
import org.espressoOtr.exs.sql.SqlSearchResultTable;
import org.espressoOtr.exs.sql.param.SearchResultRecord;
import org.espressootr.lib.collection.ListDistributor;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchManager
{
    Logger logger = LoggerFactory.getLogger(SearchManager.class);
    
    Barista baristaIndex = Barista.getInstance();
    
    StoringCache storingCache = StoringCache.getInstance();
    
    MessageQueue msgQ = MessageQueue.getInstance();
    
    ApiManager apiManager = null;
    
    public SearchManager()
    {
        apiManager = new ApiManager();
    }
    
    /***
     * 1) Search from Internal index and DB. 2) Search from OpenAPI.
     * 
     * @param ExsRequestParam
     * @return ExsResponseParam
     * @throws Exception
     */
    public ExsResponseParam search(ExsRequestParam exsReqParam) throws Exception
    {
        
        ExsResponseParam exsResParam = new ExsResponseParam();
        
        List<SearchResult> searchResult = null;
        List<String> reqCodeList = getRequestCode(exsReqParam.getKeyword());
        
        logger.info("reqCodeList : {}", reqCodeList);
        
        if (reqCodeList.size() == 0)
        {
            logger.info("Get Data from OPEN API");
            searchResult = searchFromOpenApi(exsReqParam);
            
        }
        else
        {
            logger.info("Get Data from DB");
            searchResult = searchFromDb(reqCodeList, exsReqParam);
        }
        
        if (searchResult != null)
        {
            exsResParam.setOutputCount(searchResult.size());
            exsResParam.setResultList(searchResult);
        }
        
        if (isSaving(exsResParam.getOutputCount(), reqCodeList.size()))
        {
            String requestCode = storingCache.add(exsReqParam, exsResParam);
            msgQ.add(CommandType.STORE, requestCode);
        }
        
        return exsResParam;
        
    }
    
    private boolean isSaving(int outputCount, int requestCodeCount)
    {
        if (outputCount > 0 && requestCodeCount == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    /***
     * Search using ApiManager.
     * 
     * @param exsReqParam
     * @return
     * @throws Exception
     */
    private List<SearchResult> searchFromOpenApi(ExsRequestParam exsReqParam) throws Exception
    {
        apiManager.request(exsReqParam.getDomain(), exsReqParam.getKeyword(), exsReqParam.getOutputCount(), exsReqParam.getPageNo());
        return apiManager.response();
    }
    
    /***
     * Search from DB. 
     * @param reqCodeList
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<SearchResult> searchFromDb(List<String> reqCodeList, ExsRequestParam exsReqParam)
    {
        List<SearchResult> searchResult = new ArrayList<SearchResult>();
        List<SearchResultRecord> sqlSearchResultData = new ArrayList<SearchResultRecord>();
        
        for (String requestCode : reqCodeList)
        {
            sqlSearchResultData.addAll(SqlSearchResultTable.select(requestCode));
        }
         
        HashMap<Integer, List> pagePerResult = ListDistributor.distributeListToSameCapacity(exsReqParam.getOutputCount(), sqlSearchResultData);
         
        for (SearchResultRecord srr : (List<SearchResultRecord>) pagePerResult.get(exsReqParam.getPageNo() - 1))
        {
            TextSearchResult textSearchResult = new TextSearchResult();
            textSearchResult.setTitle(srr.getTitle());
            textSearchResult.setSnippet(srr.getSnippet());
            textSearchResult.setLink(srr.getLink());
            
            searchResult.add(textSearchResult);
        }
        
        return searchResult;
    }

    /***
     * Get Intersected ReqeustCode from Barista Index.
     * 
     * @param searchKeyword
     * @return List<String> requestCodeList
     */
    private List<String> getRequestCode(String searchKeyword)
    {
        String[] kwds = searchKeyword.split(" ");
        
        IntersectionMap ictMap = new IntersectionMap();
        
        for (String kwd : kwds)//
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

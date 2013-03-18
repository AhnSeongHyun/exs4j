package org.espressoOtr.exs.server.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.espressoOtr.exs.api.manager.ApiManager;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;
import org.espressoOtr.exs.cmd.CommandType;
import org.espressoOtr.exs.common.Properties;
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
    
    int mode = 2;
    
    private final int OPENAPI_MODE = 0;
    
    private final int DB_MODE = 1;
    
    private final int HYBRID_MODE = 2;
    
    public SearchManager()
    {
        apiManager = new ApiManager();
        
        String modeStr = System.getProperty(Properties.MODE);
        if (modeStr != null)
        {
            mode = Integer.parseInt(modeStr);
        }
        
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
        boolean isSaving = false;
        
        if (mode == OPENAPI_MODE)
        {
            logger.info("OPENAPI_MODE:{}", exsReqParam.getKeyword());
            
            searchResult = searchFromOpenApi(exsReqParam);
            
            isSaving = isSaving(searchResult.size());
            
        }
        else if (mode == DB_MODE)
        {
            
            List<String> reqCodeList = getRequestCode(exsReqParam.getKeyword());
            
            logger.info("DB_MODE:{}   reqCodeList:{}", exsReqParam.getKeyword(), reqCodeList.toString());
            
            searchResult = searchFromDb(reqCodeList, exsReqParam);
            
            logger.info("DB_MODE:{} searchResult size:{}", exsReqParam.getKeyword(), searchResult.size());
            
            isSaving = false;
        }
        else
        {
            logger.info("HYBRID_MODE:{}", exsReqParam.getKeyword());
            
            List<String> reqCodeList = getRequestCode(exsReqParam.getKeyword());
            
            if (reqCodeList.size() == 0)
                searchResult = searchFromOpenApi(exsReqParam);
            
            else
                searchResult = searchFromDb(reqCodeList, exsReqParam);
            
            isSaving = isSaving(searchResult.size(), reqCodeList.size());
            
        }
        
        if (searchResult != null)
        {
            exsResParam.setOutputCount(searchResult.size());
            exsResParam.setResultList(searchResult);
        }
        
        if (isSaving)
        
        {
            String requestCode = storingCache.add(exsReqParam, exsResParam);
            msgQ.add(CommandType.STORE, requestCode);
        }
        
        return exsResParam;
        
    }
    
    /***
     * Save or not
     * 
     * @param outputCount
     * @return
     */
    private boolean isSaving(int outputCount)
    {
        if (outputCount > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
        
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<SearchResult> searchFromDb(List<String> reqCodeList, ExsRequestParam exsReqParam)
    {
        List<SearchResult> searchResult = new ArrayList<SearchResult>();
        
        try
        {
            
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
        }
        catch (Exception e)
        {
            logger.info("{}", e);
            logger.info("{}", reqCodeList);
            logger.info("{}", exsReqParam.toString());
            
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

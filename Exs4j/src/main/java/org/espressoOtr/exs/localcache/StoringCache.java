package org.espressoOtr.exs.localcache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.espressoOtr.exs.api.result.TextSearchResult;
import org.espressoOtr.exs.server.params.ExsRequestParam;
import org.espressoOtr.exs.server.params.ExsResponseParam;
import org.espressoOtr.exs.sql.param.RequestRecord;
import org.espressoOtr.exs.sql.param.SearchResultRecord;
import org.espressootr.lib.string.StringAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * LocalCache for Sorting Index and DB. 
 * @author AhnSeongHyun
 *
 */
public class StoringCache
{
    
    Logger logger = LoggerFactory.getLogger(StoringCache.class);
    
    private static final StoringCache sharedObject = new StoringCache();
    
    private List<RequestRecord> totalRequestRecords = new ArrayList<RequestRecord>();
    
    private List<SearchResultRecord> totalSearchResultRecords = new ArrayList<SearchResultRecord>();
    
    private StoringCache()
    {
        throw new AssertionError();
    }
    
    public static StoringCache getInstance()
    {
        return sharedObject;
    }
    
    /***
     * Add ExsReqiesetParam and ExsResponseParam to totalRequestRecords, totalSearchResultRecords
     * @param ExsReqiesetParam
     * @param ExsResponseParam
     * @return Current RequestCode; 
     */
    public String add(ExsRequestParam requestParam, ExsResponseParam responseParam)
    {
        
        RequestRecord requestRecord = new RequestRecord();
        requestRecord.setReqDate(new Date());
        requestRecord.setMasterKeyword(requestParam.getKeyword());
        requestRecord.setOrigin(requestParam.getDomain());
        
        String requestCode = StringAppender.mergeToStr(requestRecord.getMasterKeyword(), requestRecord.getOrigin(), String.valueOf(requestRecord.getReqDate().hashCode()));
        
        requestCode = String.valueOf(requestCode.hashCode());
        requestRecord.setRequestCode(requestCode);
        
        int responseSize = responseParam.getOutputCount();
        for (int i = 0; i < responseSize; i++)
        {
            
            TextSearchResult tsr = (TextSearchResult) responseParam.getResultList().get(i);
            
            SearchResultRecord srr = new SearchResultRecord();
            srr.setLink(tsr.getLink());
            srr.setTitle(tsr.getTitle());
            srr.setSnippet(tsr.getSnippet());
            srr.setRequestCode(requestCode);
            srr.setDocId(requestCode + String.valueOf(i));
            
            totalSearchResultRecords.add(srr);
        }
        
        totalRequestRecords.add(requestRecord);
        
        return requestRecord.getRequestCode();
        
    }
    
    /**
     * StoringCache clear. 
     */
    public void clear()
    {
        totalRequestRecords.clear();
        totalSearchResultRecords.clear();
        
    }
    
    public void sizeToString()
    {
        logger.info("TRR:{} TSR:{}", this.totalRequestRecords.size(), this.totalSearchResultRecords.size());
        
    }
    
    /***
     * Get RequestRecord by given requestCode.
     * @param requestCode
     * @return
     */
    public RequestRecord getRequestRecord(String requestCode)
    {
        
        RequestRecord returnRequestRecord = new RequestRecord();
        
        int totalRequestRecordsSize = this.totalRequestRecords.size();
        int willRemoveIndex = -1;
        for (int i = 0; i < totalRequestRecordsSize; i++)
        {
            if (this.totalRequestRecords.get(i).getRequestCode().equals(requestCode))
            {
                returnRequestRecord.setRequestCode(this.totalRequestRecords.get(i).getRequestCode());
                returnRequestRecord.setMasterKeyword(this.totalRequestRecords.get(i).getMasterKeyword());
                returnRequestRecord.setOrigin(this.totalRequestRecords.get(i).getOrigin());
                returnRequestRecord.setReqDate(this.totalRequestRecords.get(i).getReqDate());
                
                willRemoveIndex = i;
                break;
            }
        }
        
        if (willRemoveIndex != -1)
        {
            this.totalRequestRecords.remove(willRemoveIndex);
        }
        
        return returnRequestRecord;
        
    }
    
    
    /***
     * Get SearchReusltRecord List by given requestCode. 
     * @param requestCode
     * @return  List<SearchResultRecord>
     */
    public List<SearchResultRecord> getSearchResultRecord(String requestCode)
    {
        
        List<SearchResultRecord> returnSearchResultRecords = new ArrayList<SearchResultRecord>();
        
        int totalSearchResultRecordsSize = this.totalSearchResultRecords.size();
        List<Integer> willRemoveIndexs = new ArrayList<Integer>();
        for (int i = 0; i < totalSearchResultRecordsSize; i++)
        {
            if (this.totalSearchResultRecords.get(i).getRequestCode().equals(requestCode))
            {
                SearchResultRecord returnSearchResultRecord = new SearchResultRecord();
                returnSearchResultRecord.setRequestCode(this.totalSearchResultRecords.get(i).getRequestCode());
                returnSearchResultRecord.setDocId(this.totalSearchResultRecords.get(i).getDocId());
                returnSearchResultRecord.setTitle(this.totalSearchResultRecords.get(i).getTitle());
                returnSearchResultRecord.setSnippet(this.totalSearchResultRecords.get(i).getSnippet());
                returnSearchResultRecord.setLink(this.totalSearchResultRecords.get(i).getLink());
                
                returnSearchResultRecords.add(returnSearchResultRecord);
                
                willRemoveIndexs.add(i);
            }
        }
        
        if (willRemoveIndexs.size() != 0)
        {
            int arrangeIndex = 0;
            for (int willRemoveIndex : willRemoveIndexs)
            {
                this.totalSearchResultRecords.remove(willRemoveIndex - arrangeIndex);
                arrangeIndex++;
            }
        }
        
        return returnSearchResultRecords;
        
    }
    
}

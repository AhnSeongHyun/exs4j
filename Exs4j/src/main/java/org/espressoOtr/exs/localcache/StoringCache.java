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

public class StoringCache
{
    // TODO: 실제 CS INDEX 및 DB 에 저장 전에 저장하는 장소
    // TODO: 싱글턴
    
    Logger logger = LoggerFactory.getLogger(StoringCache.class);
    
    private static final StoringCache sharedObject = new StoringCache();
    
    private List<RequestRecord> totalRequestRecords = new ArrayList<RequestRecord>(); 
    private List<SearchResultRecord> totalSearchResultRecords = new ArrayList<SearchResultRecord>();
    
    private StoringCache()
    {
    }
    
    public static StoringCache getInstance()
    {
        return sharedObject;
    }
    
    public String add(ExsRequestParam requestParam, ExsResponseParam responseParam)
    {
        
        RequestRecord requestRecord = new RequestRecord();
        requestRecord.setReqDate(new Date());
        requestRecord.setMasterKeyword(requestParam.getKeyword());
        requestRecord.setOrigin(requestParam.getDomain());
        
        requestRecord.setRequestCode(StringAppender.mergeToStr(requestRecord.getMasterKeyword(), requestRecord.getOrigin(), "_", String.valueOf(requestRecord.getReqDate().hashCode())));
        
        int responseSize = responseParam.getOutputCount();
        for (int i = 0; i < responseSize; i++)
        {
            
            TextSearchResult tsr = (TextSearchResult)responseParam.getResultList().get(i);
            
            SearchResultRecord srr = new SearchResultRecord();
            srr.setLink(tsr.getLink());
            srr.setTitle(tsr.getTitle());
            srr.setSnippet(tsr.getSnippet());
            srr.setRequestCode(requestRecord.getRequestCode());
            srr.setDocId(requestRecord.getRequestCode() + String.valueOf(i));
            
            totalSearchResultRecords.add(srr); 
        }
        
        totalRequestRecords.add(requestRecord);
        
        return requestRecord.getRequestCode();
        
    }
    
    public void clear()
    {
        totalRequestRecords.clear();
        totalSearchResultRecords.clear();
        
    }
    

    public void sizeToString()
    {
        logger.info("TRR:{} TSR:{}", this.totalRequestRecords.size(), this.totalSearchResultRecords.size());
        
    }

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
    
    public SearchResultRecord getSearchResultRecord(String requestCode)
    {
      
        SearchResultRecord returnSearchResultRecord = new SearchResultRecord();
        
        int totalSearchResultRecordsSize = this.totalSearchResultRecords.size();
        List<Integer> willRemoveIndexs = new ArrayList<Integer>();
        for (int i = 0; i < totalSearchResultRecordsSize; i++)
        {
            if (this.totalSearchResultRecords.get(i).getRequestCode().equals(requestCode))
            {
                returnSearchResultRecord.setRequestCode(this.totalSearchResultRecords.get(i).getRequestCode());
                returnSearchResultRecord.setDocId(this.totalSearchResultRecords.get(i).getDocId());
                returnSearchResultRecord.setTitle(this.totalSearchResultRecords.get(i).getTitle());
                returnSearchResultRecord.setSnippet(this.totalSearchResultRecords.get(i).getSnippet());
                returnSearchResultRecord.setLink(this.totalSearchResultRecords.get(i).getLink());
                
                willRemoveIndexs.add(i);
            }
        }
        
        if (willRemoveIndexs.size() != 0)
        {
            int arrangeIndex = 0; 
            for(int willRemoveIndex : willRemoveIndexs)
            {
                this.totalSearchResultRecords.remove(willRemoveIndex-arrangeIndex);
                arrangeIndex++; 
            }
        }
        
        return returnSearchResultRecord;
        
    }
    
    
}

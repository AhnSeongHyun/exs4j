package org.espressoOtr.exs.cmd.process;

import java.util.List;

import org.espressoOtr.exs.cmd.Command;
import org.espressoOtr.exs.index.Barista;
import org.espressoOtr.exs.localcache.StoringCache;
import org.espressoOtr.exs.sql.SqlRequsetTable;
import org.espressoOtr.exs.sql.SqlSearchResultTable;
import org.espressoOtr.exs.sql.param.RequestRecord;
import org.espressoOtr.exs.sql.param.SearchResultRecord; 
import org.espressootr.lib.utils.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreCmdProc implements CommandProcessor
{
    
    Logger logger = LoggerFactory.getLogger(StoreCmdProc.class);
    
    @Override
    public void process(Command cmd)
    {
        try
        {
            String requestCode = String.valueOf(cmd.getForwardValue());
            
            if (ParamUtil.isValidParams(requestCode))
            {
                StoringCache storingCache = StoringCache.getInstance();
                
                RequestRecord requestRecord = storingCache.getRequestRecord(requestCode);
                List<SearchResultRecord> searchResultRecords = storingCache.getSearchResultRecord(requestCode);
                
                // CS Indexing
                storeShelfer(requestRecord);
                
                // DB Insert
                storeDb(requestRecord, searchResultRecords);
                
            }
        }
        catch (Exception e)
        {
            logger.error("{}", e);
        }
    }
    
    private void storeDb(RequestRecord requestRecord, List<SearchResultRecord> searchResultRecords)
    {
        try
        {
            SqlRequsetTable.insert(requestRecord);
            for (SearchResultRecord searchResultRecord : searchResultRecords)
            {
                SqlSearchResultTable.insert(searchResultRecord);
            }
        }
        catch (Exception e)
        {
            logger.error("{}", e);
        }
    }
    
    private void storeShelfer(RequestRecord requestRecord)
    {
        try
        { 
            Barista barista = Barista.getInstance(); 
            barista.add(requestRecord.getRequestCode(), requestRecord.getMasterKeyword()); 
        }
        catch (Exception e)
        {
            logger.error("{}", e);
        }
        
    }
    
}

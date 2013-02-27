package org.espressoOtr.exs.cmd.process;

import org.espressoOtr.exs.cmd.Command;
import org.espressoOtr.exs.localcache.StoringCache;
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
        String requestCode = String.valueOf(cmd.getForwardValue());
        
        if(ParamUtil.isValidParams(requestCode))
        {
            StoringCache sc = StoringCache.getInstance();
            
            RequestRecord rr = sc.getRequestRecord(requestCode);
            SearchResultRecord srr =  sc.getSearchResultRecord(requestCode);
            
            sc.sizeToString();
            
            
            //TODO:가져와서, CS INDEX
            //TODO:가져와서, DB INSERT 
            
        }
        
        
    }
    
}

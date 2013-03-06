package org.espressoOtr.exs.index;
  
import org.espressootr.lib.collection.cs.map.MapShelfer;
import org.espressootr.lib.utils.SplitterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Barista
{
    
    private static final Barista sharedObject = new Barista();
    
    Logger logger = LoggerFactory.getLogger(Barista.class);
    
    
    private MapShelfer indexShelfer = new MapShelfer();
    
    private Barista()
    {
    }
    
    public static Barista getInstance()
    {
        return sharedObject;
    }

    public void add(String requestCode, String masterKeyword)
    {
        String[] parsedMasterKwds = parseMasterKeyword(masterKeyword);
        
        for(String parsedMasterKwd : parsedMasterKwds)
        {
            this.indexShelfer.add(parsedMasterKwd.trim(), requestCode);
        }
        
        logger.info(this.indexShelfer.toString());
        
    }
    
    
    private String[] parseMasterKeyword(String masterKeyword)
    {
        return masterKeyword.split(SplitterUtil.SPACE);
    }
}

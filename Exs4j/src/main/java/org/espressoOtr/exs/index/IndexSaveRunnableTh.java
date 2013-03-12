package org.espressoOtr.exs.index;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.espressootr.lib.collection.cs.multmap.MultiMapCanister;
import org.espressootr.lib.encoding.Encoding;
import org.espressootr.lib.string.StringAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Save MultiMapCanister to index file using json format.  
 * @author AhnSeongHyun
 *
 */
public class IndexSaveRunnableTh implements Runnable
{
    List<MultiMapCanister> willSaveMmCanisterList = null;
    
    String fileName = "db";
    
    Logger logger = LoggerFactory.getLogger(IndexSaveRunnableTh.class);
    
    public IndexSaveRunnableTh(List<MultiMapCanister> list, int fileIndex)
    {
        this.willSaveMmCanisterList = list;
        this.fileName = StringAppender.mergeToStr("./",  fileName , String.valueOf(fileIndex), ".rdb");
        
    }
    
    @Override
    public void run()
    {
        try
        {
            BufferedWriter indexSaver = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName, false), Encoding.getJVMEncoding()));
            
            StringBuilder sb = new StringBuilder();
            
            for (MultiMapCanister mmCanister : willSaveMmCanisterList)
            {
                
                indexSaver.write(mmCanister.toJson());
                indexSaver.newLine();
                
                sb.delete(0, sb.length());
            }
            
            indexSaver.close();
        }
        catch (Exception e)
        {
            logger.error("{}", e);
        }
    }
}

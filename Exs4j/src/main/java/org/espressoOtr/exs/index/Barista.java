package org.espressoOtr.exs.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.espressootr.lib.collection.ListDistributor;
import org.espressootr.lib.collection.cs.multmap.MultiMapCanister;
import org.espressootr.lib.collection.cs.multmap.MultiMapShelfer;
import org.espressootr.lib.file.filter.CustomFileFilter;
import org.espressootr.lib.file.filter.TargetType;
import org.espressootr.lib.parallel.ParallelCount;
import org.espressootr.lib.utils.SplitterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Barista
{
    
    private static final Barista sharedObject = new Barista();
    
    Logger logger = LoggerFactory.getLogger(Barista.class);
    
    private MultiMapShelfer indexShelfer = new MultiMapShelfer();
    
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
        
        for (String parsedMasterKwd : parsedMasterKwds)
        {
            this.indexShelfer.add(parsedMasterKwd.trim(), requestCode);
        }
        
        logger.info(this.indexShelfer.toString());
        
    }
    
    private String[] parseMasterKeyword(String masterKeyword)
    {
        return masterKeyword.split(SplitterUtil.SPACE);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void save()
    {
        List<MultiMapCanister> mmCanisterList = this.indexShelfer.getShelf();
        ExecutorService concurrentService = Executors.newFixedThreadPool(ParallelCount.availableParallelCount());
        
        Map<Integer, List> distMmCanisterMap = ListDistributor.distributeListToSubList(ParallelCount.availableParallelCount(), mmCanisterList);
        
        logger.info("saving.. canisterCount : {}", mmCanisterList.size());
        
        int size = distMmCanisterMap.size();
        
        
        for (int i = 0; i < size; i++)
        {
            concurrentService.execute(new IndexSaveRunnableTh((List<MultiMapCanister>) distMmCanisterMap.get(i), i));
        }
        
        concurrentService.shutdown();
        logger.info("saving finished. canisterCount : {}", mmCanisterList.size());
    }
    
    public void load()
    {
        try
        {
            List<String> willLoadFilePathList = getValidFileList();
            
            logger.info("load file list : {}", willLoadFilePathList);
            
            ExecutorService concurrentService = Executors.newFixedThreadPool(ParallelCount.availableParallelCount());
            List<Future<List<MultiMapCanister>>> indexLoadThResults = new ArrayList<Future<List<MultiMapCanister>>>();
            int size = willLoadFilePathList.size();
            
            for (int i = 0; i < size; i++)
            {
                indexLoadThResults.add(concurrentService.submit(new IndexLoadCallableTh(willLoadFilePathList.get(i))));
            }
            
            for (Future<List<MultiMapCanister>> loadResult : indexLoadThResults)
            {
                
                for (MultiMapCanister multiMapCanister : loadResult.get())
                {
                    this.indexShelfer.add(multiMapCanister);
                    
                }
                
            }
            
            logger.info("load finished : {}", this.indexShelfer.toString());
            
            concurrentService.shutdown();
        }
        catch (Exception e)
        {
            logger.error("{}", e);
        }
        
    }
    
    public List<String> getValidFileList()
    {
        List<String> filePathList = new ArrayList<String>();
        
        File dir = new File("./");
        
        CustomFileFilter cusromFileFilter = new CustomFileFilter(TargetType.ENDS_WITH, "rdb");
        
        for (File cusromFile : dir.listFiles(cusromFileFilter))
        {
            filePathList.add(cusromFile.getPath());
        }
        
        return filePathList;
    }
    
    /***
     * 
     * @param seachKeyword
     *            separated word ex) googgle python => 1:google 2:python
     * @return
     */ 
    public List<String> getRequestCodes(String searchKeyword)
    {
        List<String> requestCodes = new ArrayList<String>();
        
        for( Object obj : this.indexShelfer.search(searchKeyword))
        {
            requestCodes.add(obj.toString());
        }
        
        return requestCodes;
    }
    
}

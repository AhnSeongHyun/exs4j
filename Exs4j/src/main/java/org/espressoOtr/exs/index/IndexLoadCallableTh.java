package org.espressoOtr.exs.index;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.espressootr.lib.collection.cs.multmap.MultiMapCanister;
import org.espressootr.lib.file.FileReaderManager;
import org.espressootr.lib.utils.InitUtil;

import com.google.gson.Gson;

public class IndexLoadCallableTh implements Callable<List<MultiMapCanister>>
{
    String indexFilePath = InitUtil.EMPTY_STRING;
    
    public IndexLoadCallableTh(String filePath)
    {
        this.indexFilePath = filePath;
    }
    
    @Override
    public List<MultiMapCanister> call() throws Exception
    {
        List<MultiMapCanister> mmCanisterList = new ArrayList<MultiMapCanister>();
        Gson gson = new Gson();
        
        List<String> jsonStrFromFile = FileReaderManager.readLineFromFile(this.indexFilePath);
        
        for (String json : jsonStrFromFile)
        { 
            mmCanisterList.add(gson.fromJson(json, MultiMapCanister.class)); 
        }
        
        return mmCanisterList;
    }
    
}

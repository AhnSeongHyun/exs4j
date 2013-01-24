package org.espressoOtr.exs.api;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.espressoOtr.exs.common.Path;
import org.espressootr.lib.file.FileReaderManager;

public class ApiKey
{
    
    private String APIKEY = null;
    
    public ApiKey(Object classType)
    {
        
        try
        {
            APIKEY = getKeyFromKeyFile(Path.getRemainKeyFilePath(classType));
        }
        catch (IOException e)
        {
            
            e.printStackTrace();
        }
        
    }
     
    private String getKeyFromKeyFile(String keyFilePath) throws IOException
    {
        String remainKeyFilePath = keyFilePath;
        
        List<String> remainKeyList = FileReaderManager.readLineFromFile(remainKeyFilePath);
        
        return extractKey(remainKeyList);
    }
    
    private String extractKey(List<String> remainKeyList)
    {
        
        int keyCount = remainKeyList.size();
        
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(keyCount);
        
        return remainKeyList.get(randomIndex);
        
    }
    
    public String getKey()
    {
        return this.APIKEY;
        
    }
    
    public void setKey(String apiKey)
    {
        this.APIKEY = apiKey;
        
    }
}

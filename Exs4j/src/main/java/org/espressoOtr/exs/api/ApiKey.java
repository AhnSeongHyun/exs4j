package org.espressoOtr.exs.api;

import java.util.Random;

 
import org.espressoOtr.exs.common.Properties;
import org.espressootr.lib.utils.InitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiKey
{
    
    private String APIKEY = null; 

    Logger logger = LoggerFactory.getLogger(ApiKey.class);
    
    public ApiKey(String className)
    {
        APIKEY = getKeyString(className);
    }
    
    private String getKeyString(String className)
    { 
        
        logger.info("{}", className);
        
        String apiKey = InitUtil.EMPTY_STRING;
        String propertyKey = InitUtil.EMPTY_STRING;
        
        
        if (className.equals("NaverApi"))
        {
            propertyKey = Properties.NAVER_KEY;
        }
        else if (className.equals("DaumApi"))
        {
            propertyKey = Properties.DAUM_KEY;
                   
        }
        else if (className.equals("BingApi"))
        {
            propertyKey = Properties.BING_KEY;
        }
        
        apiKey = getRandomKey(System.getProperty(propertyKey));
        
        return apiKey;
    }
    
    private String getRandomKey(String keys)
    {
        String[] keyArr = keys.split(",");
        
        Random oRandom = new Random();
        
        int ri = oRandom.nextInt(keyArr.length) + 0;
        
        return keyArr[ri];
        
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

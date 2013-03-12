package org.espressoOtr.exs.api;

import java.util.Random;

 
import org.espressoOtr.exs.common.Properties;
import org.espressoOtr.exs.constant.OpenApi;
import org.espressootr.lib.utils.InitUtil;
import org.espressootr.lib.utils.SplitterUtil;
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
    
    /***
     * Get open api key from JVM properties. 
     * @param className
     * @return
     */
    private String getKeyString(String className)
    { 
        
        logger.info("{}", className);
        
        String apiKey = InitUtil.EMPTY_STRING;
        String propertyKey = InitUtil.EMPTY_STRING;
        
        
        if (className.equals(OpenApi.NAVER_API))
        {
            propertyKey = Properties.NAVER_KEY;
        }
        else if (className.equals(OpenApi.DAUM_API))
        {
            propertyKey = Properties.DAUM_KEY;
                   
        }
        else if (className.equals(OpenApi.BING_API))
        {
            propertyKey = Properties.BING_KEY;
        }
        
        apiKey = getRandomKey(System.getProperty(propertyKey));
        
        return apiKey;
    }
    
    /***
     * If open api key is multiple value, get key randomly. 
     * @param keys
     * @return
     */
    private String getRandomKey(String keys)
    {
        String[] keyArr = keys.split(SplitterUtil.COMMA);
        
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

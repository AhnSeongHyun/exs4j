package org.espressoOtr.exs.api;

import java.util.Random;

import org.espressoOtr.exs.api.bing.BingApi;
import org.espressoOtr.exs.api.daum.DaumApi;
import org.espressoOtr.exs.api.naver.NaverApi;
import org.espressoOtr.exs.common.Properties;
import org.espressootr.lib.utils.InitUtil;

public class ApiKey
{
    
    private String APIKEY = null; 
    
    
    public ApiKey(Object classType)
    {
        APIKEY = getKeyString(classType);
    }
    
    private String getKeyString(Object classType)
    { 
        String apiKey = InitUtil.EMPTY_STRING;
        String propertyKey = InitUtil.EMPTY_STRING;
        
        
        if (classType.getClass() == NaverApi.class)
        {
            propertyKey = Properties.NAVER_KEY;
        }
        else if (classType.getClass() == DaumApi.class)
        {
            propertyKey = Properties.DAUM_KEY;
                   
        }
        else if (classType.getClass() == BingApi.class)
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

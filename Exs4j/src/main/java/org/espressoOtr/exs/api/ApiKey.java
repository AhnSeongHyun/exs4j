package org.espressoOtr.exs.api;

import java.util.Random;

import org.espressoOtr.exs.api.bing.BingAPI;
import org.espressoOtr.exs.api.daum.DaumAPI;
import org.espressoOtr.exs.api.naver.NaverAPI;

public class ApiKey
{
    
    private String APIKEY = null;
    
    public ApiKey(Object classType)
    {
        APIKEY = getKeyString(classType);
    }
    
    private String getKeyString(Object classType)
    { 
        String apiKey = "";
        
        String propertyKey = "";
        if (classType.getClass() == NaverAPI.class)
        {
            propertyKey = "naver_key";
        }
        else if (classType.getClass() == DaumAPI.class)
        {
            propertyKey = "daum_key";
                   
        }
        else if (classType.getClass() == BingAPI.class)
        {
            propertyKey = "bing_key";
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

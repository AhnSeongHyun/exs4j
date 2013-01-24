package org.espressoOtr.exs.test;

import static org.junit.Assert.*;

import org.espressoOtr.exs.api.bing.BingAPI;
import org.espressoOtr.exs.api.daum.DaumAPI;
import org.espressoOtr.exs.api.daum.DaumAPITarget;
import org.espressoOtr.exs.api.naver.NaverAPI;
import org.espressoOtr.exs.api.naver.NaverAPITarget;
import org.junit.Before;
import org.junit.Test;

public class UT_ApiKey
{
    

    @Before
    public void setUp() throws Exception
    { 
       System.setProperty("naver_key", "776db691996584df9385aa576bd4dcef");
       System.setProperty("daum_key", "ffb014a6c553ddd4e93ce7af11ba075a75dd8e34");
       System.setProperty("bing_key", "XewnW370WT5kdz9ECFie99d5miv/2777Hg0dt0kBp10=");
       
    }
    
    @Test
    public void test_NaverKey()
    {
        NaverAPI naverApi = new NaverAPI();
        naverApi.SetTarget(NaverAPITarget.NEWS);
        
        naverApi.request("python");
        assertEquals(10, naverApi.response().size()); 
        
    }
    
    @Test
    public void test_DaumKey()
    {
        DaumAPI daumApi = new DaumAPI();
        daumApi.SetTarget(DaumAPITarget.BLOG);
        
        daumApi.request("python");
        assertEquals(10, daumApi.response().size()); 
    }
    
    
    @Test
    public void test_BingKey()
    {
        BingAPI bingApi = new BingAPI();
        bingApi.request("python");
        assertEquals(10, bingApi.response().size());
        
    }
    
    
    
}

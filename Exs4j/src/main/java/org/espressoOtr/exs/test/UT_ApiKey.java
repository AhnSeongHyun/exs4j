package org.espressoOtr.exs.test;

import static org.junit.Assert.*;

import org.espressoOtr.exs.api.bing.BingApi;
import org.espressoOtr.exs.api.daum.DaumApi;
import org.espressoOtr.exs.api.daum.DaumApiTarget;
import org.espressoOtr.exs.api.naver.NaverApi;
import org.espressoOtr.exs.api.naver.NaverApiTarget;
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
        NaverApi naverApi = new NaverApi();
        naverApi.setTarget(NaverApiTarget.NEWS);
        
        naverApi.request("python");
        assertEquals(10, naverApi.response().size());
        
    }
    
    @Test
    public void test_DaumKey()
    {
        DaumApi daumApi = new DaumApi();
        daumApi.setTarget(DaumApiTarget.BLOG);
        
        daumApi.request("python");
        assertEquals(10, daumApi.response().size());
    }
    
    @Test
    public void test_BingKey()
    {
        BingApi bingApi = new BingApi();
        bingApi.request("python");
        assertEquals(10, bingApi.response().size());
        
    }
    
}

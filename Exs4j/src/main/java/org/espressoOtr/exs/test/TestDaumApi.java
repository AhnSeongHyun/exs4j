package org.espressoOtr.exs.test;

import static org.junit.Assert.*;

import org.espressoOtr.exs.api.ApiManager;
import org.espressoOtr.exs.api.daum.DaumApi;
import org.espressoOtr.exs.api.daum.DaumApiTarget; 
import org.junit.Before;
import org.junit.Test;

public class TestDaumApi
{
  ApiManager apiManager = null; 
    
    @Before
    public void setUp() throws Exception
    { 
        System.setProperty("naver_key", "776db691996584df9385aa576bd4dcef");
        System.setProperty("daum_key", "ffb014a6c553ddd4e93ce7af11ba075a75dd8e34");
        System.setProperty("bing_key", "XewnW370WT5kdz9ECFie99d5miv/2777Hg0dt0kBp10=");
        
        apiManager = new ApiManager();
    } 
    
    
    @Test
    public void test_daum_blog()
    {
        DaumApi daumApi = new DaumApi();
        daumApi.setTarget(DaumApiTarget.BLOG);
        
        daumApi.request("python");
        assertEquals(10, daumApi.response().size());
    }
  
    @Test
    public void test_daum_web()
    {
        DaumApi daumApi = new DaumApi();
        daumApi.setTarget(DaumApiTarget.WEB);
        
        daumApi.request("python");
        assertEquals(10, daumApi.response().size());
    }
    

    @Test
    public void test_daum_cafe()
    {
        DaumApi daumApi = new DaumApi();
        daumApi.setTarget(DaumApiTarget.CAFE);
        
        daumApi.request("python");
        assertEquals(10, daumApi.response().size());
    }
  
}

package org.espressoOtr.exs.api.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

public class MathSearchResult implements SearchResult
{
    
    public String title;
    public List<String> contents = Collections.emptyList();
    
    
    public MathSearchResult()
    {
        title = "";
        contents = new ArrayList<String>();
        
        
    }
    
    
    public String toJson()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
    
    public void PrintResult()
    {
        
        System.out.println("=====================================================");
        System.out.println(this.title);
        
        for (String content : this.contents) 
        {
            System.out.println(content);    
        }
        
        System.out.println("=====================================================");
    }
    
}

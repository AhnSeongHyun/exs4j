package org.espressoOtr.exs.api.result;

import com.google.gson.Gson;

public class TextSearchResult implements SearchResult
{

    public String title; 
    public String link; 
    public String snippet; 
    
    
    public TextSearchResult()
    {
        title = "";
        link = "";
        snippet = "";
        
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
        System.out.println(this.link);
        System.out.println(this.snippet);
        System.out.println("=====================================================");
    }

  
}

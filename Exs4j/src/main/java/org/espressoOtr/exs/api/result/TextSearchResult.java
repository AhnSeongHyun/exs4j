package org.espressoOtr.exs.api.result;

import org.espressootr.lib.json.JsonBodum;
 

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
        String json =  JsonBodum.toJson(this);
        return json;
    }
    
    public void printResult()
    {
        
        System.out.println("=====================================================");
        System.out.println(this.title);
        System.out.println(this.link);
        System.out.println(this.snippet);
        System.out.println("=====================================================");
    }

  
}

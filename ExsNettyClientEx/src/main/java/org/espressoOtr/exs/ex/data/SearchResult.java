package org.espressoOtr.exs.ex.data;

public class SearchResult
{
    public String title;
    public String link;
    public String snippet;
    
    public void PrintResult()
    { 
        System.out.println("=====================================================");
        System.out.println(this.title);
        System.out.println(this.link);
        System.out.println(this.snippet);
        System.out.println("=====================================================");
    }
    
}

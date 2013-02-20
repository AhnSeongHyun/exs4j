package org.espressoOtr.exs.api;

import java.util.List; 

import org.espressoOtr.exs.api.result.SearchResult;



public interface SearchApi  
{ 
    public void request(String keyword);
    public List<SearchResult> response();
    public String getAPIName(); 
    public int getOutputCount();
    public void setOutputCount(int outputCount);
    
    public int getPageNo();
    public void setPageNo(int pageNo);
}

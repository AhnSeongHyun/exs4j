package org.espressoOtr.exs.server.params;

import java.util.List;

import org.espressoOtr.exs.api.result.SearchResult;
import org.espressootr.lib.json.JsonBodum;

 
public class ExsResponseParam
{
    private int outputCount = 0;
    private List<SearchResult> resultList = null;
    
    public int getOutputCount()
    {
        return outputCount;
    }
    
    public void setOutputCount(int outputCount)
    {
        this.outputCount = outputCount;
    }
    
    public List<SearchResult> getResultList()
    {
        return resultList;
    }
    
    public void setResultList(List<SearchResult> resultList)
    {
        this.resultList = resultList;
    }
    
    public String toString()
    {
        return JsonBodum.toJson(this); 
    }
    
    
}

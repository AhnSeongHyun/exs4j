package org.espressoOtr.exs.ex.data;

import java.util.List;

public class ExsResponseParam
{
    private int outputCount;
    
    private List<SearchResult> resultList;

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
    
}

package org.espressoOtr.exs.server.params;

import java.util.List;

import org.espressoOtr.exs.api.result.SearchResult;

/**
 * 
 * @author hnSeongHyun(sh84.ahn@gmail.com) EXS Server Response Parameter Use
 *         ExsResponseParam class to JSon String
 */
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
    
}

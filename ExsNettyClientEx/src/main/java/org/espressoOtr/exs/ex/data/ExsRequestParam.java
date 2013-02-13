package org.espressoOtr.exs.ex.data;

public class ExsRequestParam
{
    private String keyword;
    
    private String domain;
    
    private int outputCount;
    
    private int pageNo;
    
    public String getKeyword()
    {
        return keyword;
    }
    
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }
    
    public String getDomain()
    {
        return domain;
    }
    
    public void setDomain(String domain)
    {
        this.domain = domain;
    }
    
    public int getOutputCount()
    {
        return outputCount;
    }
    
    public void setOutputCount(int outputCount)
    {
        this.outputCount = outputCount;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
}

package org.espressoOtr.exs.server.params;

import org.espressootr.lib.utils.InitUtil;

/**
 * @author hnSeongHyun(sh84.ahn@gmail.com) EXS Server Request Parameter Use
 *         ExsReuqestParam to JSon String.
 */
public class ExsRequestParam
{
    private String keyword = InitUtil.EMPTY_STRING;
    private String domain = InitUtil.EMPTY_STRING;
    private int outputCount = 0;
    private int pageNo = 0;
    
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

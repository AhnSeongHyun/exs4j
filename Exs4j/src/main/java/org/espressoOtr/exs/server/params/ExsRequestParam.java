package org.espressoOtr.exs.server.params;

 
import org.espressootr.lib.json.JsonBodum;
import org.espressootr.lib.utils.InitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
public class ExsRequestParam
{
    private String keyword = InitUtil.EMPTY_STRING;
    private String domain = InitUtil.EMPTY_STRING;
    private int outputCount = 0;
    private int pageNo = 0;
    
    Logger logger = LoggerFactory.getLogger(ExsRequestParam.class);
    
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
     
    public String toString()
    {
        return JsonBodum.toJson(this); 
    }
    
  
}

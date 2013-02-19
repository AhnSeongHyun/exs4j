package org.espressoOtr.exs.sql.param;

import java.util.Date;

import org.espressootr.lib.utils.InitUtil;

public class RequestRecord
{
    private String masterKeyword = InitUtil.EMPTY_STRING;
    private String origin  = InitUtil.EMPTY_STRING;
    private Date  reqDate;
    
    public String getMasterKeyword()
    {
        return masterKeyword;
    }
    public void setMasterKeyword(String masterKeyword)
    {
        this.masterKeyword = masterKeyword;
    }
    
    public Date getReqDate()
    {
        return reqDate;
    }
    public void setReqDate(Date reqDate)
    {
        this.reqDate = reqDate;
    }
    public String getOrigin()
    {
        return origin;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    
}

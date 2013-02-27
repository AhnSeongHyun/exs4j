package org.espressoOtr.exs.sql.param;

import org.espressootr.lib.utils.InitUtil;

public class SearchResultRecord
{
    private String requestCode =  InitUtil.EMPTY_STRING;
    private String docId = InitUtil.EMPTY_STRING; 
    private String title = InitUtil.EMPTY_STRING; 
    private String snippet = InitUtil.EMPTY_STRING;
    private String link = InitUtil.EMPTY_STRING;
    
   
    public String getRequestCode()
    {
        return requestCode;
    }
    public void setRequestCode(String requestCode)
    {
        this.requestCode = requestCode;
    }
    public String getDocId()
    {
        return docId;
    }
    public void setDocId(String docId)
    {
        this.docId = docId;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getSnippet()
    {
        return snippet;
    }
    public void setSnippet(String snippet)
    {
        this.snippet = snippet;
    }
    public String getLink()
    {
        return link;
    }
    public void setLink(String link)
    {
        this.link = link;
    } 
    
    
}

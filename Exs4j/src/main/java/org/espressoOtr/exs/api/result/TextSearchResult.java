package org.espressoOtr.exs.api.result;

import org.espressootr.lib.json.JsonBodum;
import org.espressootr.lib.string.StringAppender;
import org.espressootr.lib.utils.InitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextSearchResult implements SearchResult
{
    
    private String title = InitUtil.EMPTY_STRING;
    private String link = InitUtil.EMPTY_STRING;
    private String snippet = InitUtil.EMPTY_STRING;
    
    Logger logger = LoggerFactory.getLogger(TextSearchResult.class);
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getLink()
    {
        return link;
    }
    
    public void setLink(String link)
    {
        this.link = link;
    }
    
    public String getSnippet()
    {
        return snippet;
    }
    
    public void setSnippet(String snippet)
    {
        this.snippet = snippet;
    }
    
    public TextSearchResult()
    {
        title = InitUtil.EMPTY_STRING;
        link = InitUtil.EMPTY_STRING;
        snippet = InitUtil.EMPTY_STRING;
        
    }
    
    public String toJson()
    {
        String json = JsonBodum.toJson(this);
        return json;
    }
    
    public void printResult()
    {
        
        logger.info("=====================================================");
        logger.info(this.title);
        logger.info(this.link);
        logger.info(this.snippet);
        logger.info("=====================================================");
    }
    
    @Override
    public String toString()
    {
        return StringAppender.mergeToStr(this.toJson(), "\n");
    }
}

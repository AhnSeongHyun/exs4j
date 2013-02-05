package org.espressoOtr.exs.api.daum.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class DaumWebData implements DaumData
{
    
    private String title;
    
    private String link;
    
    private String description;
    
    private String generator;
    
    private String lastBuildDate;
    
    private String totalCount;
    
    private String pageCount;
    
    private String result;
    
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
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getLastBuildDate()
    {
        return lastBuildDate;
    }
    
    public void setLastBuildDate(String lastBuildDate)
    {
        this.lastBuildDate = lastBuildDate;
    }
    
    public String getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(String totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public String getPageCount()
    {
        return pageCount;
    }
    
    public void setPageCount(String pageCount)
    {
        this.pageCount = pageCount;
    }
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public List<DaumWebItem> getItemList()
    {
        return itemList;
    }
    
    public void setItemList(List<DaumWebItem> itemList)
    {
        this.itemList = itemList;
    }
    
    public String getGenerator()
    {
        return generator;
    }
    
    public void setGenerator(String generator)
    {
        this.generator = generator;
    }
    
    @XmlElement(name = "item")
    private List<DaumWebItem> itemList;
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DaumWebItem
    {
        private String title;
        
        private String link;
        
        private String description;
        
        private String url;
        
        private String pubDate;
        
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
        
        public String getDescription()
        {
            return description;
        }
        
        public void setDescription(String description)
        {
            this.description = description;
        }
        
        public String getPubDate()
        {
            return pubDate;
        }
        
        public void setPubDate(String pubDate)
        {
            this.pubDate = pubDate;
        }
        
        public String getUrl()
        {
            return url;
        }
        
        public void setUrl(String url)
        {
            this.url = url;
        }
    }
    
    @Override
    public List<SearchResult> toSearchResult()
    {
        int itemSize = this.getItemList().size();
        
        List<SearchResult> resultList = new ArrayList<SearchResult>();
        
        for (int i = 0; i < itemSize; i++)
        {
            TextSearchResult tsr = new TextSearchResult();
            tsr.setTitle(this.getItemList().get(i).getTitle());
            tsr.setLink(this.getItemList().get(i).getLink());
            tsr.setSnippet(this.getItemList().get(i).getDescription());
            
            resultList.add(tsr);
        }
        return resultList;
    }
    
}

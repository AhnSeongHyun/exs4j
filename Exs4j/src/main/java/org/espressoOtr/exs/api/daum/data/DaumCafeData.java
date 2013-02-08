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
public class DaumCafeData implements DaumData
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
    
    public List<DaumCafeItem> getItemList()
    {
        return itemList;
    }
    
    public void setItemList(List<DaumCafeItem> itemList)
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
    private List<DaumCafeItem> itemList;
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DaumCafeItem
    {
        private String title;
        
        private String link;
        
        private String description;
        
        private String cafeName;
        
        private String cafeUrl;
        
        private String pubDate;
        
        private String link_blod;
        
        private String cmt_count;
        
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
        
        public String getCafeName()
        {
            return cafeName;
        }
        
        public void setCafeName(String cafeName)
        {
            this.cafeName = cafeName;
        }
        
        public String getCafeUrl()
        {
            return cafeUrl;
        }
        
        public void setCafeUrl(String cafeUrl)
        {
            this.cafeUrl = cafeUrl;
        }
        
        public String getLink_blod()
        {
            return link_blod;
        }
        
        public void setLink_blod(String link_blod)
        {
            this.link_blod = link_blod;
        }
        
        public String getCmt_count()
        {
            return cmt_count;
        }
        
        public void setCmt_count(String cmt_count)
        {
            this.cmt_count = cmt_count;
        }
    }
    
    @Override
    public List<SearchResult> toSearchResult()
    {
        List<SearchResult> resultList = new ArrayList<SearchResult>();
        
        if (Integer.parseInt(this.totalCount) != 0)
        {
            int itemSize = this.getItemList().size();
            
            for (int i = 0; i < itemSize; i++)
            {
                TextSearchResult tsr = new TextSearchResult();
                tsr.setTitle(this.getItemList().get(i).getTitle());
                tsr.setLink(this.getItemList().get(i).getLink());
                tsr.setSnippet(this.getItemList().get(i).getDescription());
                
                resultList.add(tsr);
            }
        }
        return resultList;
    }
    
}

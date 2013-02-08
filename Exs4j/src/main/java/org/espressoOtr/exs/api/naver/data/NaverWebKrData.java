package org.espressoOtr.exs.api.naver.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.espressoOtr.exs.api.result.SearchResult;
import org.espressoOtr.exs.api.result.TextSearchResult;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class NaverWebKrData implements NaverData
{
    private Channel channel;
    
    public Channel getChannel()
    {
        return channel;
    }
    
    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Channel
    {
        private String title;
        
        private String link;
        
        private String description;
        
        private String lastBuildDate;
        
        private String total;
        
        private String start;
        
        private String display;
        
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
        
        public int getTotal()
        {
            return Integer.parseInt(total);
        }
        
        public void setTotal(String total)
        {
            this.total = total;
        }
        
        public String getStart()
        {
            return start;
        }
        
        public void setStart(String start)
        {
            this.start = start;
        }
        
        public String getDisplay()
        {
            return display;
        }
        
        public void setDisplay(String display)
        {
            this.display = display;
        }
        
        public List<NaverWebKrItem> getItemList()
        {
            return itemList;
        }
        
        public void setItemList(List<NaverWebKrItem> itemList)
        {
            this.itemList = itemList;
        }
        
        @XmlElement(name = "item")
        private List<NaverWebKrItem> itemList;
        
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class NaverWebKrItem
        {
            
            private String title;
            
            private String link;
            
            private String description;
            
            public String getLink()
            {
                return link;
            }
            
            public void setLink(String link)
            {
                this.link = link;
            }
            
            public String getTitle()
            {
                return title;
            }
            
            public void setTitle(String title)
            {
                this.title = title;
            }
            
            public String getDescription()
            {
                return description;
            }
            
            public void setDescription(String description)
            {
                this.description = description;
            }
        }
    }
    
    @Override
    public List<SearchResult> toSearchResult()
    {
        List<SearchResult> resultList = new ArrayList<SearchResult>();
        if (Integer.parseInt(this.getChannel().total) != 0)
        {
            int itemSize = this.getChannel().getItemList().size();
            
            for (int i = 0; i < itemSize; i++)
            {
                TextSearchResult tsr = new TextSearchResult();
                tsr.setTitle(this.getChannel().getItemList().get(i).getTitle());
                tsr.setLink(this.getChannel().getItemList().get(i).getLink());
                tsr.setSnippet(this.getChannel().getItemList().get(i).getDescription());
                
                resultList.add(tsr);
            }
        }
        
        return resultList;
    }
}

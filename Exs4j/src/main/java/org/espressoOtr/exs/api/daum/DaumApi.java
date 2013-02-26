package org.espressoOtr.exs.api.daum;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.espressoOtr.exs.api.ApiKey;
import org.espressoOtr.exs.api.SearchApi;
import org.espressoOtr.exs.api.daum.data.DaumBlogData;
import org.espressoOtr.exs.api.daum.data.DaumCafeData;
import org.espressoOtr.exs.api.daum.data.DaumData;
import org.espressoOtr.exs.api.daum.data.DaumWebData;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressootr.lib.string.StringAppender;
import org.espressootr.lib.utils.InitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class DaumApi implements SearchApi
{
    
    private String uri = InitUtil.EMPTY_STRING;
    
    private DaumApiTarget target;
    
    private List<SearchResult> searchResultList = Collections.emptyList();
    
    private ApiKey apiKey = null;
    
    private String sort = "date";
    
    private int outputCountLimit = 50;
    
    private int outputCountDefault = 10;
    
    private int pageNoLimit = 500;
    
    private int pageNoDefault = 1;
    
    private int outputCount = outputCountDefault; // default.
    
    private int pageNo = pageNoDefault; // default
    
    Logger logger = LoggerFactory.getLogger(DaumApi.class);
    
    public DaumApi(ApiKey daumApiKey)
    {
        this.apiKey = daumApiKey;
    }
    
    public DaumApi()
    {
        this.apiKey = new ApiKey(DaumApi.class.getSimpleName());
    }
    
    public void setTarget(DaumApiTarget target)
    {
        
        this.target = target;
    }
    
    public DaumApiTarget getTarget()
    {
        return this.target;
        
    }
    
    public String getTargetString()
    {
        String targetString = InitUtil.EMPTY_STRING;
        
        if (getTarget() == DaumApiTarget.BLOG)
        {
            targetString = "blog";
        }
        else if (getTarget() == DaumApiTarget.CAFE)
        {
            targetString = "cafe";
        }
        else if (getTarget() == DaumApiTarget.WEB)
        {
            targetString = "web";
        }
        
        return targetString;
        
    }
    
    public void request(String keyword)
    {
        
        logger.info(DaumApi.class.getName() + " KEYWORD : " + "'" + keyword + "'");
        
        try
        {
            requestDaumAPI(keyword);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void requestDaumAPI(String keyword) throws IOException, SAXException, ParserConfigurationException
    {
        uri = getURI(keyword);
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = null;
        
        DaumData obj = null;
        
        try
        {
            System.out.println(uri);
            httpGet = new HttpGet(uri);
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine status = response.getStatusLine();
            
            System.out.println(status);
            
            if (status.getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity1 = response.getEntity();
                
                Source source = new StreamSource(entity1.getContent());
                System.out.println(source.toString());
                
                JAXBContext jc = null;
                
                if (this.getTarget() == DaumApiTarget.BLOG)
                    jc = JAXBContext.newInstance(DaumBlogData.class);
                else if (this.getTarget() == DaumApiTarget.CAFE)
                    jc = JAXBContext.newInstance(DaumCafeData.class);
                
                else if (this.getTarget() == DaumApiTarget.WEB) jc = JAXBContext.newInstance(DaumWebData.class);
                
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                
                obj = (DaumData) unmarshaller.unmarshal(source);
            }
            else
            {
                throw new Exception("HTTP Response Status Code : " + status.getStatusCode());
            }
            
        }
        catch (Exception e)
        {
            
            e.printStackTrace();
        }
        finally
        {
            httpGet.releaseConnection();
        }
        
        this.searchResultList = obj.toSearchResult();
        
    }
    
    private String getURI(String keyword) throws IOException
    {
        
        String subUri = getSubURI();
        
        String createdUri = StringAppender.mergeToStr("http://apis.daum.net/search/", getTargetString(), "?q=", URLEncoder.encode(keyword, "UTF-8"), subUri, "&apikey=", this.apiKey.getKey()
         );
        
        return createdUri;
    }
    
    private String getSubURI()
    {
        
        StringBuilder subUriSb = new StringBuilder();
        
        subUriSb.append("&reuslt");
        subUriSb.append(String.valueOf(this.outputCount));
        subUriSb.append("&pageno=");
        subUriSb.append(String.valueOf(this.pageNo));
        
        if (getTarget() == DaumApiTarget.BLOG)
        { 
            subUriSb.append("&sort=");
            subUriSb.append(sort);
            
        }
        else if (getTarget() == DaumApiTarget.CAFE)
        { 
            subUriSb.append("&sort=");
            subUriSb.append(sort);
            
        }
        
        subUriSb.append("&output=xml");
        
        return subUriSb.toString();
    }
    
    public List<SearchResult> response()
    {
        
        logger.debug(DaumApi.class.getName() + " result : " + this.searchResultList.size());
        
        return this.searchResultList;
        
    }
    
    public String getAPIName()
    {
        return this.getClass().getName();
        
    }
    
    public String getSort()
    {
        return this.sort;
    }
    
    public void setSort(String sort)
    {
        this.sort = sort;
        
    }
    
    @Override
    public int getOutputCount()
    {
        return this.outputCount;
    }
    
    @Override
    public void setOutputCount(int outputCount)
    {
        
        if (outputCount > this.outputCountLimit)
        {
            this.outputCount = this.outputCountDefault;
        }
        else
        {
            this.outputCount = outputCount;
        }
    }
    
    @Override
    public int getPageNo()
    {
        return this.pageNo;
    }
    
    @Override
    public void setPageNo(int pageNo)
    {
        
        if (pageNo > this.pageNoLimit)
        {
            this.pageNo = this.pageNoDefault;
        }
        else
        {
            this.pageNo = pageNo;
        }
        
    }
}

package org.espressoOtr.exs.api.naver;

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
import org.espressoOtr.exs.api.naver.data.NaverBlogData;
import org.espressoOtr.exs.api.naver.data.NaverCafeArticleData;
import org.espressoOtr.exs.api.naver.data.NaverData;
import org.espressoOtr.exs.api.naver.data.NaverNewsData;
import org.espressoOtr.exs.api.naver.data.NaverWebKrData;
import org.espressoOtr.exs.api.result.SearchResult;
import org.espressootr.lib.string.StringAppender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class NaverApi implements SearchApi
{
    
    private String uri = "";
    
    private NaverApiTarget target;
    
    private List<SearchResult> searchResultList = Collections.emptyList();
    
    private ApiKey apiKey = null;
    
    private String sort = "date";
    
    private int outputCountDefault = 10;
    
    private int outputCountLimit = 100;
    
    private int pageNoDefault = 1;
    
    private int pageNoLimit = 1000;
    
    private int outputCount = outputCountDefault;// default
    
    private int pageNo = pageNoDefault; // default
    
    Logger logger = LoggerFactory.getLogger(NaverApi.class);
    
    public NaverApi(ApiKey naverApiKey)
    {
        this.apiKey = naverApiKey;
    }
    
    public NaverApi()
    {
        this.apiKey = new ApiKey(NaverApi.class.getSimpleName());
    }
    
    public void setTarget(NaverApiTarget target)
    {
        
        this.target = target;
    }
    
    public String getTargetString()
    {
        String targetString = "";
        
        if (getTarget() == NaverApiTarget.BLOG)
        {
            targetString = "blog";
        }
        else if (getTarget() == NaverApiTarget.CAFEARTICLE)
        {
            targetString = "cafearticle";
        }
        else if (getTarget() == NaverApiTarget.NEWS)
        {
            targetString = "news";
        }
        else if (getTarget() == NaverApiTarget.WEBKR)
        {
            targetString = "webkr";
        }
        
        return targetString;
        
    }
    
    public NaverApiTarget getTarget()
    {
        return this.target;
        
    }
    
    public void request(String keyword)
    {
        
        logger.info(NaverApi.class.getName() + " KEYWORD : " + "'" + keyword + "'");
        
        try
        {
            requestNaverAPI(keyword); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void requestNaverAPI(String keyword) throws IOException, ParserConfigurationException, SAXException
    {
        
        uri = getURI(keyword);
        
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = null;
        
        NaverData obj = null;
        try
        {
            httpGet = new HttpGet(uri);
            HttpResponse response = httpclient.execute(httpGet);
            StatusLine status = response.getStatusLine();
            
            if (status.getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity1 = response.getEntity();
                
                Source source = new StreamSource(entity1.getContent());
                
                JAXBContext jc = null;
                
                if (this.getTarget() == NaverApiTarget.BLOG)
                    jc = JAXBContext.newInstance(NaverBlogData.class);
                else if (this.getTarget() == NaverApiTarget.CAFEARTICLE)
                    jc = JAXBContext.newInstance(NaverCafeArticleData.class);
                
                else if (this.getTarget() == NaverApiTarget.WEBKR)
                    jc = JAXBContext.newInstance(NaverWebKrData.class);
                
                else if (this.getTarget() == NaverApiTarget.NEWS) jc = JAXBContext.newInstance(NaverNewsData.class);
                
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                
                obj = (NaverData) unmarshaller.unmarshal(source);
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
        
        String createdUri = StringAppender.mergeToStr("http://openapi.naver.com/search?key=", this.apiKey.getKey(), "&target=", this.getTargetString(), "&query=", URLEncoder.encode(keyword, "UTF-8"),
                                                      subUri);
        
        return createdUri;
    }
    
    private String getSubURI()
    {
        
        StringBuilder subUriSb = new StringBuilder();
        
        subUriSb.append("&display=");
        subUriSb.append(String.valueOf(this.outputCount));
        subUriSb.append("&start=");
        subUriSb.append(String.valueOf(this.pageNo));
        
        if (getTarget() == NaverApiTarget.BLOG)
        {
            subUriSb.append("&sort=");
            subUriSb.append(this.sort);
            
        }
        
        else if (getTarget() == NaverApiTarget.CAFEARTICLE)
        {
            subUriSb.append("&sort=");
            subUriSb.append(this.sort);
            
        }
        else if (getTarget() == NaverApiTarget.NEWS)
        {
            subUriSb.append("&sort=");
            subUriSb.append(this.sort);
        }
        return subUriSb.toString();
        
    }
    
    public List<SearchResult> response()
    {
        logger.debug(NaverApi.class.getName() + " result : " + this.searchResultList.size());
        
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

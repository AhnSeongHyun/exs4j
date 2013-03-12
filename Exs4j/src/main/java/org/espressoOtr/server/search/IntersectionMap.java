package org.espressoOtr.server.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 

public class IntersectionMap
{
    private Map<String, Integer> reqCodeIntersec = new HashMap<String, Integer>();
    
    int maxCount = -1;
    
    public void add(String reqCode)
    {
        int refCount = 1;
        
        if (reqCodeIntersec.containsKey(reqCode))
        {
            refCount = reqCodeIntersec.get(reqCode);
            refCount++;
            
        }
        
        if (maxCount < refCount) maxCount = refCount;
        
        reqCodeIntersec.put(reqCode, refCount);
        
    }
    
    public void clear()
    {
        reqCodeIntersec.clear();
    }
    
    public List<String> getDescKeyList()
    {
        List<String> reqCodeList = new ArrayList<String>();
        
        if (this.maxCount != 1)
        {
            List<RequestCodeCount> reqCodeCountList = convMapToRequestCodeCount();
            this.sort(reqCodeCountList);
            
            int size = reqCodeCountList.size();
            
            for (int i = 0; i < size; i++)
            {
                reqCodeList.add(reqCodeCountList.get(i).getRequestCode());
            }
        }
        else
        {
            reqCodeList.addAll(this.reqCodeIntersec.keySet());
        }
        
        return reqCodeList;
    }
    
    private List<RequestCodeCount> convMapToRequestCodeCount()
    {
        List<RequestCodeCount> reqCodeCountList = new ArrayList<RequestCodeCount>();
        
        for (String reqCode : this.reqCodeIntersec.keySet())
        {
            reqCodeCountList.add(new RequestCodeCount(reqCode, this.reqCodeIntersec.get(reqCode)));
        }
        
        return reqCodeCountList;
    }
    
    private static final Comparator<RequestCodeCount> requestCodeCounDescComparator = new Comparator<RequestCodeCount>() {
        
        public int compare(RequestCodeCount arg0, RequestCodeCount arg1)
        {
            
            int result = 0;
            
            if (arg0.getRefCount() > arg1.getRefCount())
            {
                result = -1;
            }
            else if (arg0.getRefCount() < arg1.getRefCount())
            {
                result = 1;
            }
            else
            {
                result = 0;
            }
            
            return result;
        }
        
    };
    
    private void sort(List<RequestCodeCount> reqCodeCountList)
    {
        Collections.sort(reqCodeCountList, requestCodeCounDescComparator);
    }
    
    private class RequestCodeCount
    {
        private String requestCode;
        
        private int refCount;
        
        @SuppressWarnings("unused")
        private RequestCodeCount(){}
        
        
        public RequestCodeCount(String requestCode, int refCount)
        {
            this.requestCode = requestCode;
            this.refCount = refCount;
            
        }
        
        public String getRequestCode()
        {
            return requestCode;
        }
        
        public int getRefCount()
        {
            return refCount;
        }
        
    }
    
}

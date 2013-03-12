package org.espressoOtr.exs.mngserver.params;

import org.espressootr.lib.json.JsonBodum;

public class ExsMngResponseParam
{
    private String response;
    
    public String getResponse()
    {
        return response;
    }
    
    public void setResponse(String response)
    {
        this.response = response;
    }
    
    public String toString()
    {
        return JsonBodum.toJson(this);
        
    }
}

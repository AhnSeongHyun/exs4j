package org.espressoOtr.exs.mngserver.params;

import org.espressootr.lib.json.JsonBodum;

public class ExsMngRequestParam
{
    private String command;
    
    public String getCommand()
    {
        return command;
    }
    
    public void setCommand(String command)
    {
        this.command = command;
    }
    
    public String toString()
    {
        return JsonBodum.toJson(this);
        
    }
    
}

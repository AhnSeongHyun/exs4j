package org.espressoOtr.exs.cmd;

import org.espressootr.lib.string.StringAppender;
import org.espressootr.lib.utils.InitUtil;

public class Command
{
    private CommandType cmdType;
    
    private Object forwardValue;
    
    @SuppressWarnings("unused")
    private Command()
    {
        
    }
    
    public Command(CommandType cmdType, Object value)
    {
        this.cmdType = cmdType;
        this.forwardValue = value;
        
    }
    
    public Object getForwardValue()
    {
        return forwardValue;
    }
    
    public void setForwardValue(Object forwardValue)
    {
        this.forwardValue = forwardValue;
    }
    
    public CommandType getCmdType()
    {
        return cmdType;
    }
    
    public void setCmdType(CommandType cmdType)
    {
        this.cmdType = cmdType;
    }
    
    @Override
    public String toString()
    {
        String toStr = InitUtil.EMPTY_STRING;
        
        if (this.forwardValue != null)
        {
            toStr = StringAppender.mergeToStr("CmdType:", this.cmdType.name(), "    Object:", this.forwardValue.toString());
        }
        else
        {
            toStr = StringAppender.mergeToStr("CmdType:", this.cmdType.name(), "    Object:null");
        }
        
        return toStr;
        
    }
    
}

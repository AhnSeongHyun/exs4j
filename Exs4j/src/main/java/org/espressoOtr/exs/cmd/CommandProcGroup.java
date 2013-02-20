package org.espressoOtr.exs.cmd;

 
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandProcGroup
{
    private static final CommandProcGroup sharedObject = new CommandProcGroup();
    
    private static Map<Command, CommandProcessor> cmdProcessors  = new HashMap<Command, CommandProcessor>();
    
     
    Logger logger = LoggerFactory.getLogger(CommandProcGroup.class);
    
    static
    {
        cmdProcessors.put(Command.STOP, new StopCmdProc());
        
    }
    
    
    private CommandProcGroup()
    {
        
    }
    
    public static CommandProcGroup getInstance()
    {
        return sharedObject;
    }
    
    public CommandProcessor getProc(Command cmd)
    {
       return cmdProcessors.get(cmd);
    }
     
     
}

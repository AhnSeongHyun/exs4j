package org.espressoOtr.exs.cmd.process;

import java.util.HashMap;
import java.util.Map;

import org.espressoOtr.exs.cmd.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandProcGroup
{
    private static final CommandProcGroup sharedObject = new CommandProcGroup();
    
    private static Map<CommandType, CommandProcessor> cmdProcessors = new HashMap<CommandType, CommandProcessor>();
    
    Logger logger = LoggerFactory.getLogger(CommandProcGroup.class);
    
    static
    {
        cmdProcessors.put(CommandType.STOP, new StopCmdProc());
        cmdProcessors.put(CommandType.STORE, new StoreCmdProc());
        cmdProcessors.put(CommandType.SAVE, new SaveCmdProc());
        cmdProcessors.put(CommandType.LOAD, new LoadCmdProc());
        
    }
    
    private CommandProcGroup()
    {
        
    }
    
    public static CommandProcGroup getInstance()
    {
        return sharedObject;
    }
    
    public CommandProcessor getProc(CommandType cmdType)
    {
        return cmdProcessors.get(cmdType);
    }
    
}

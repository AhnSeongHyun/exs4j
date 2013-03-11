package org.espressoOtr.exs.messageq;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.espressoOtr.exs.cmd.Command;
import org.espressoOtr.exs.cmd.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageQueue
{
    private static final MessageQueue sharedObject = new MessageQueue();
    
    private Queue<Command> msgQ = new ConcurrentLinkedQueue<Command>();
    
    Logger logger = LoggerFactory.getLogger(MessageQueue.class);
    
    private MessageQueue()
    {
    }
    
    public static MessageQueue getInstance()
    {
        return sharedObject;
    }
    
    public void add(Command msg)
    {
        this.msgQ.add(msg);
        logger.info("msgQ size : {}", msgQ.size());
        
    }
    
    public void add(CommandType cmdType, Object value)
    {
        this.msgQ.add(new Command(cmdType, value));
        
    }
    
    public void add(String msg)
    {
        
        this.msgQ.add(new Command(stringToCommand(msg), null));
        
    }
    
    private CommandType stringToCommand(String msg)
    {
        CommandType cmdType = CommandType.NONE;
        if (msg.equalsIgnoreCase(CommandType.STOP.name()))
        {
            cmdType = CommandType.STOP;
        }
        else if (msg.equalsIgnoreCase(CommandType.STORE.name()))
        {
            cmdType = CommandType.STORE;
        }
        else if (msg.equalsIgnoreCase(CommandType.SAVE.name()))
        {
            cmdType = CommandType.SAVE;
        } 
        else if (msg.equalsIgnoreCase(CommandType.LOAD.name()))
        {
            cmdType = CommandType.LOAD; 
        } 
        else
        {
            cmdType = CommandType.NONE;
        }
        
        return cmdType;
        
    }
    
    public Command get()
    {
        Command cmd = this.msgQ.poll();
        logger.info("msgQ size : {}", msgQ.size());
        return cmd;
        
    }
    
    public int size()
    {
        return this.msgQ.size();
        
    }
}

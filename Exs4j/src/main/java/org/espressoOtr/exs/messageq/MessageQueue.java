package org.espressoOtr.exs.messageq;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.espressoOtr.exs.cmd.Command;
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
    
    public void add(String msg)
    {
        
        this.msgQ.add(stringToCommand(msg));
        logger.info("msgQ size : {}", msgQ.size());
        
    }
    
    private Command stringToCommand(String msg)
    {
        Command cmd = Command.NONE;
        if (msg.equalsIgnoreCase(Command.STOP.name()))
        {
            cmd = Command.STOP;
        }
        else
        {
            cmd = Command.NONE;
        }
        
        return cmd;
        
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

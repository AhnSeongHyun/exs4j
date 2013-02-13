package org.espressoOtr.exs.ex.client; 

import org.espressoOtr.exs.ex.data.ExsResponseParam;
import org.espressoOtr.exs.ex.data.SearchResult;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExsClientExHandler extends SimpleChannelHandler
{
    Logger logger = LoggerFactory.getLogger(ExsClientExHandler.class);
    
    public ExsClientExHandler()
    {
        
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
    {
        ExsResponseParam recvExsData = (ExsResponseParam) e.getMessage();
        for(SearchResult sr : recvExsData.getResultList())
        {
            sr.PrintResult();
        } 
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
    { 
        e.getCause().printStackTrace();
        
        Channel ch = e.getChannel();
        
        ch.close();
    }
}

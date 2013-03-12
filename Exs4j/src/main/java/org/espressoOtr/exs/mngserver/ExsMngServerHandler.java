package org.espressoOtr.exs.mngserver;

import org.espressoOtr.exs.messageq.MessageQueue;
import org.espressoOtr.exs.mngserver.params.ExsMngResponseParam;
import org.espressoOtr.exs.server.ExsServerHandler;
import org.espressootr.lib.string.StringAppender;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture; 
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExsMngServerHandler extends SimpleChannelHandler
{
    
    static final ChannelGroup allChannels = new DefaultChannelGroup("admin-server");
    
    final String connectedMsg = "[connected] ";
    
    final String receivedMsg = "[recvd] ";
    
    final String disconnectedMsg = "[disconnected] ";
    
    String msg = "";
    
    MessageQueue msgQ = MessageQueue.getInstance();
    
    Logger logger = LoggerFactory.getLogger(ExsServerHandler.class);
    
    public ExsMngServerHandler()
    {
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
    { 
        String msgStr = new String(((ChannelBuffer) e.getMessage()).array());
        logger.info("e.getMeessage():{}", msgStr);
        msgQ.add(msgStr); // All messages from ExsMngPort are added to MessageQ. 
        
    }
    
    public ChannelFuture sendResponseParam(Channel responseChannel, ExsMngResponseParam exsMngResponseParam)
    { 
        return responseChannel.write(exsMngResponseParam); 
    }
    
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
    {// if client connect, call this.
    
        Channel connectedChannel = e.getChannel();
        allChannels.add(connectedChannel);
        
        msg = StringAppender.mergeToStr(connectedMsg, e.getChannel().getLocalAddress().toString(), " , Connections:", String.valueOf(allChannels.size()));
        logger.info(msg);
        
    }
    
    @Override
    public void channelDisconnected(ChannelHandlerContext crx, ChannelStateEvent e)
    {
        msg = StringAppender.mergeToStr(disconnectedMsg, e.getChannel().getLocalAddress().toString(), " , Connections:", String.valueOf(allChannels.size()));
        logger.info(msg);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
    {
        e.getCause().printStackTrace();
        logger.info(msg);
    }
    
}

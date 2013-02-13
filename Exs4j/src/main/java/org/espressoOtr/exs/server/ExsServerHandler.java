package org.espressoOtr.exs.server;

import org.espressoOtr.exs.api.ApiManager;
import org.espressoOtr.exs.server.params.ExsRequestParam;
import org.espressoOtr.exs.server.params.ExsResponseParam;

import org.espressootr.lib.string.StringAppender;
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

public class ExsServerHandler extends SimpleChannelHandler
{
    
    static final ChannelGroup allChannels = new DefaultChannelGroup("time-server");
    
    final String connectedMsg = "[connected] ";
    
    final String receivedMsg = "[recvd] ";
    
    final String disconnectedMsg = "[disconnected] ";
    
    String msg = "";
    
    ApiManager apiManager = null;
    
    Logger logger = LoggerFactory.getLogger(ExsServerHandler.class);
    
  
    public ExsServerHandler()
    {
        apiManager = new ApiManager();
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
    {
        logger.info("e.getMeessage():{}", e.getMessage().toString());
        
        ExsRequestParam recvExsData = (ExsRequestParam) e.getMessage();
        apiManager.request(recvExsData.getDomain(), recvExsData.getKeyword(), recvExsData.getOutputCount(), recvExsData.getPageNo());
        
        // response
        ExsResponseParam exsResponseParam = new ExsResponseParam();
        exsResponseParam.setResultList(apiManager.response());
        exsResponseParam.setOutputCount(exsResponseParam.getResultList().size());
        
        ChannelFuture result = sendCrxResponseParam(e.getChannel(), exsResponseParam);
        
        if (result.isSuccess() == true)
            msg = "SEND SUCCESS.";
        else
            msg = "SEND FAIL.";
        
        logger.info(msg);
        
    }
    
    public ChannelFuture sendCrxResponseParam(Channel responseChannel, ExsResponseParam exsResponseParam)
    {
        
        logger.info("sendCrxResponseParam start");
        
        return responseChannel.write(exsResponseParam);
        
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

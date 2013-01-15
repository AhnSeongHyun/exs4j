package org.espressoOtr.exs.server;

import org.espressoOtr.exs.api.ApiManager;
import org.espressoOtr.exs.server.params.ExsRequestParam;
import org.espressoOtr.exs.server.params.ExsResponseParam;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
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

import com.google.gson.Gson;

public class ExsServerHandler extends SimpleChannelHandler
{
    
    static final ChannelGroup allChannels = new DefaultChannelGroup("time-server");
    
    final String connectedMsg = "[connected] ";
    
    final String receivedMsg = "[recvd] ";
    
    final String disconnectedMsg = "[disconnected] ";
    
    StringBuffer mergedMessage = new StringBuffer();
    
    String msg = "";
    
    Gson gson = null;
    
    ApiManager apiManager = null;
    
    char CR = '\r';
    
    char LF = '\n';
    
    Logger logger = LoggerFactory.getLogger(ExsServerHandler.class);
    
    public ExsServerHandler()
    {
        gson = new Gson();
        apiManager = new ApiManager();
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
    {
        ChannelBuffer request = (ChannelBuffer) e.getMessage();
        
        msg = new String(request.array());
        mergedMessage.append(msg);
        
        logger.info(receivedMsg + msg);
        
        if (msg.charAt(msg.length() - 2) == CR && msg.charAt(msg.length() - 1) == LF)
        {
            logger.info("validmsg");
            
            mergedMessage.append(msg);
            
            ExsRequestParam recvExsData = gson.fromJson(msg, ExsRequestParam.class);
            mergedMessage.delete(0, mergedMessage.length());
            
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
        else
        {
            logger.info("unvalidmsg");
            
            mergedMessage.append(msg);
            
        }
        
    }
    
    public ChannelFuture sendCrxResponseParam(Channel responseChannel, ExsResponseParam exsResponseParam)
    {
        StringBuffer responseJson = new StringBuffer(gson.toJson(exsResponseParam));
        responseJson.append(CR);
        responseJson.append(LF);
        
        ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
        buf.writeBytes(responseJson.toString().getBytes());
        
        return responseChannel.write(buf);
        
    }
    
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
    {// 접속
     // 할때마다
     // 알려줌.
        
        Channel connectedChannel = e.getChannel();
        allChannels.add(connectedChannel);
        
        msg = connectedMsg + e.getChannel().getLocalAddress() + " , Connections:" + allChannels.size();
        
        logger.info(msg);
        
    }
    
    @Override
    public void channelDisconnected(ChannelHandlerContext crx, ChannelStateEvent e)
    {
        msg = disconnectedMsg + e.getChannel().getLocalAddress() + " , Connections:" + allChannels.size();
        logger.info(msg);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
    {
        e.getCause().printStackTrace();
        
        Channel ch = e.getChannel();
        ch.close();
        
        msg = "ExsServer End..";
        logger.info(msg);
    }
    
}

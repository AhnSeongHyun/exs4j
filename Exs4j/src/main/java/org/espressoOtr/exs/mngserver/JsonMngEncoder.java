package org.espressoOtr.exs.mngserver;

import org.espressoOtr.exs.mngserver.params.ExsMngResponseParam;
import org.espressoOtr.exs.server.JsonEncoder; 
import org.espressootr.lib.json.JsonBodum;
import org.espressootr.lib.string.StringAppender;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMngEncoder extends OneToOneEncoder
{
    
    char CR = '\r';
    
    char LF = '\n';
    
    Logger logger = LoggerFactory.getLogger(JsonEncoder.class);
    
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
    {
        if (msg instanceof ExsMngResponseParam)
        {
            ExsMngResponseParam exsMngResponseParam = (ExsMngResponseParam) msg;
            
            String toSendJson = StringAppender.mergeToStr(JsonBodum.toJson(exsMngResponseParam), String.valueOf(CR), String.valueOf(LF));
            
            ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
            buf.writeBytes(toSendJson.getBytes());
            
            return buf;
        }
        else
        {
            
            throw new Exception("not ExsMngResponseParam type");
            
        }
        
    }
    
}

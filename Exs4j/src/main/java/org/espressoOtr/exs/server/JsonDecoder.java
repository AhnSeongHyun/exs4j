package org.espressoOtr.exs.server;

import org.espressoOtr.exs.server.params.ExsRequestParam;
import org.espressootr.lib.json.JsonBodum;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonDecoder extends OneToOneDecoder
{
    Logger logger = LoggerFactory.getLogger(JsonDecoder.class);
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
    {
        if (msg instanceof ChannelBuffer)
        {

            String msgStr = new String(((ChannelBuffer) msg).array());
            ExsRequestParam exsReqParam = JsonBodum.fromJson(msgStr, ExsRequestParam.class);
            
            return exsReqParam;
            
        }
        else
        {
            throw new Exception("not ChnnaelBuffer type");
            
        }
        
    }
    
}

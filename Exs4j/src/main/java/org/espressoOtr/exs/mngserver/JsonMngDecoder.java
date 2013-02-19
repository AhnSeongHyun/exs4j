package org.espressoOtr.exs.mngserver;
 
import org.espressoOtr.exs.mngserver.params.ExsMngRequestParam; 
import org.espressootr.lib.json.JsonBodum;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel; 
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMngDecoder extends OneToOneDecoder
{
    
    Logger logger = LoggerFactory.getLogger(JsonMngDecoder.class);
    
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
    {
        
        
        if (msg instanceof ChannelBuffer)
        {
            
            String msgStr = new String(((ChannelBuffer) msg).array());
            logger.info(msgStr);
            
            ExsMngRequestParam exsReqParam = JsonBodum.fromJson(msgStr, ExsMngRequestParam.class);
            
            return exsReqParam;
            
        }
        else
        {
            throw new Exception("not ChnnaelBuffer type");
            
        }
        
    }
}

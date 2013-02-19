package org.espressoOtr.exs.mngserver;

import static org.jboss.netty.channel.Channels.pipeline;
 
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;

public class JsonMngPipelineFactory implements ChannelPipelineFactory
{
    
    @Override
    public ChannelPipeline getPipeline() throws Exception
    {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));  
        pipeline.addLast("decoder", new JsonMngDecoder());
        pipeline.addLast("encoder", new JsonMngEncoder()); 
        pipeline.addLast("handler", new ExsMngServerHandler());
        return pipeline;
    }
    
}

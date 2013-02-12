package org.espressoOtr.exs.server;
import static org.jboss.netty.channel.Channels.*;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters; 

public class JsonPipelineFactory implements ChannelPipelineFactory
{

    @Override
    public ChannelPipeline getPipeline() throws Exception
    {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));  
        pipeline.addLast("decoder", new JsonDecoder());
        pipeline.addLast("encoder", new JsonEncoder()); 
        pipeline.addLast("handler", new ExsServerHandler());
        return pipeline;
    }
    

}

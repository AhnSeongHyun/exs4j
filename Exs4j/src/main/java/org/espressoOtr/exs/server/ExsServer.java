package org.espressoOtr.exs.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author AhnSeongHyun TCP/IP Server
 */
public class ExsServer
{
    
    private int port = 8000;
    Logger logger = LoggerFactory.getLogger(ExsServer.class);
    
    /**
     * server start port 8000
     */
    public void run()
    {
        
        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            
            public ChannelPipeline getPipeline() throws Exception
            {
                return Channels.pipeline(new ExsServerHandler());
            }
        });
        
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        
        bootstrap.bind(new InetSocketAddress(this.port));
        
        logger.info("ExsServer Started..  port:{}", this.port);
        
    }
    
    /**
     * @return the port
     */
    public int getPort()
    {
        return port;
    }
    
    /**
     * @param port
     *            the port to set
     */
    public void setPort(int port)
    {
        this.port = port;
    }
    
}
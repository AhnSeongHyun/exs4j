package org.espressoOtr.exs.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.espressoOtr.exs.common.Properties; 
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExsServer
{
    Logger logger = LoggerFactory.getLogger(ExsServer.class);
    
    private static final ExsServer sharedObject = new ExsServer();
    
    ChannelFactory factory = null;
    
    ServerBootstrap bootstrap = null;
    
    static final ChannelGroup allChannels = new DefaultChannelGroup("exs-server");
    
    private ExsServer()
    { 
    }
    
    /***
     * Get Singleton instance this class
     * @return
     */
    public static ExsServer getInstance()
    {
        return sharedObject;
    }
    
    
    /***
     * Start Exs4j Sever using JVM.Property("port");
     */
    public void start()
    {
        
        int port = Integer.parseInt(System.getProperty(Properties.PORT));
        
        factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        
        bootstrap = new ServerBootstrap(factory);
        
        bootstrap.setPipelineFactory(new JsonPipelineFactory());
        
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        
        Channel channel = bootstrap.bind(new InetSocketAddress(port));
        
        allChannels.add(channel);
        
        logger.info("ExsServer Started..  port:{}", port);
    }
    
    

    /***
     * Stop Exs4j Sever
     */
    public void stop()
    {
        ChannelGroupFuture futures = allChannels.close();
        
        futures.awaitUninterruptibly();
        
        factory.releaseExternalResources();
        
        logger.info("ExsServer Stop..");
        
    }
    
}
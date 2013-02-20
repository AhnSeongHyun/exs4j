package org.espressoOtr.exs.mngserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.espressoOtr.exs.MessageQueue;
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

public class ExsMngServer
{
    
    Logger logger = LoggerFactory.getLogger(ExsMngServer.class);
    
    private static final ExsMngServer sharedObject = new ExsMngServer();
    
    ChannelFactory factory = null;
    
    ServerBootstrap bootstrap = null;
    
    static final ChannelGroup allChannels = new DefaultChannelGroup("admin-server");
    
    private ExsMngServer()
    {
    }
    
    public static ExsMngServer getInstance()
    {
        return sharedObject;
    }
    
    public void start()
    {
        int port = Integer.parseInt(System.getProperty(Properties.PORT));
        port++;
        
        factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        
        bootstrap = new ServerBootstrap(factory);
        
        bootstrap.setPipelineFactory(new JsonMngPipelineFactory());
        
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        
        Channel channel = bootstrap.bind(new InetSocketAddress(port));
        allChannels.add(channel);
        
        logger.info("ExsMngServer Started..  port:{}", port);
        
    }
    
    public void stop()
    {
        ChannelGroupFuture futures = allChannels.close();
        
        futures.awaitUninterruptibly();
        
        factory.releaseExternalResources();
        
        logger.info("ExsMngServer Stop..");
        
    }
}

package org.espressoOtr.exs.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.espressoOtr.exs.common.Properties;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory; 
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExsServer
{
    private int port = 8000;
    Logger logger = LoggerFactory.getLogger(ExsServer.class);
    
    public ExsServer()
    {
        this.port = Integer.parseInt(System.getProperty(Properties.PORT));
    }
    
    public void run()
    {
        
        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        
         
        bootstrap.setPipelineFactory(new JsonPipelineFactory());            
        
        
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        
        bootstrap.bind(new InetSocketAddress(this.port));
        
        logger.info("ExsServer Started..  port:{}", this.port);
        
    }
    
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
}
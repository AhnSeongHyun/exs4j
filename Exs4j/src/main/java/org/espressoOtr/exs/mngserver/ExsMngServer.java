package org.espressoOtr.exs.mngserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.espressoOtr.exs.common.Properties; 
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExsMngServer
{
    private int port = 8000;
    Logger logger = LoggerFactory.getLogger(ExsMngServer.class);
    
    public ExsMngServer()
    {
        this.port = Integer.parseInt(System.getProperty(Properties.PORT));
      
    }
    
    
    public int getPort()
    {
        return port;
    }
    
  
    public void start()
    {
        
        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        
         
        bootstrap.setPipelineFactory(new JsonMngPipelineFactory());            
        
        
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);


        bootstrap.bind(new InetSocketAddress(this.port));
        
        logger.info("ExsMngServer Started..  port:{}", this.port);
        
    }
    
    
    public void stop()
    {
        // TODO Auto-generated method stub
        
    }
}
